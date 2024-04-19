package com.xht.oauth.config;


import com.xht.oauth.component.*;

import com.xht.common.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author : YIYUANYUAN
 * @description :
 * @date: 2023/12/18  21:37
 */
@Configuration
public class XhtSecurityConfig {


    @Value("${oauth.login-url}")
    private String loginUrl;

    @Value("${oauth.logout-url}")
    private String logoutUrl;

    @Autowired
    private XhtUserDetailsService xhtUserDetailsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        //配置除了登录之外的所有请求都要认证
        httpSecurity.authorizeHttpRequests(requests
                -> requests.requestMatchers(
                        "/*.html",
                        "/favicon.ico",
                        "/*/*.html",
                        "/*/*.css",
                        "/*/*.js",
                        "/swagger-resources/**",
                        "/v3/api-docs/**",
                        "/webjars/**",
                        "/swagger-ui/**",
//                            loginUrl,
                        "/oauth/get").permitAll()
                .anyRequest().authenticated());



        //开启basic认证
        httpSecurity.formLogin(httpSecurityFormLoginConfigurer -> {
            httpSecurityFormLoginConfigurer.failureHandler(xhtLoginFailureHandler());
            httpSecurityFormLoginConfigurer.successHandler(xhtLoginSuccessHandler(redisTemplate,jwtTokenUtil));
            httpSecurityFormLoginConfigurer.loginProcessingUrl(loginUrl);
        });


        //退出登录
        httpSecurity.logout(httpSecurityLogoutConfigurer ->
                {
                    httpSecurityLogoutConfigurer.logoutSuccessHandler(xhtLogoutSuccessHandler());
                    httpSecurityLogoutConfigurer.logoutUrl("/oauth/logout");
                }
        );

        //添加token过滤器
//        httpSecurity.addFilterBefore(jwtTokenSecurityFilter(), UsernamePasswordAuthenticationFilter.class);

        //添加json获取用户信息filter
        httpSecurity.addFilterAt(xhtJwtTokenSecurityFilter(jwtTokenUtil,xhtUserDetailsService,redisTemplate), UsernamePasswordAuthenticationFilter.class);

        //异常配置
        httpSecurity.exceptionHandling(httpSecurityExceptionHandling -> {
            //未登录处理的配置
            httpSecurityExceptionHandling.authenticationEntryPoint(xhtAuthenticationEntryPoint());
            //未授权处理的配置
            httpSecurityExceptionHandling.accessDeniedHandler(xhtAccessDeniedHandler());
        });

        //处理跨域
        httpSecurity.cors(httpSecurityCors -> {
            httpSecurityCors.configurationSource(corsConfigurationSource());
        });
        //关闭CSRF
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    //跨域
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");   // 允许所有的请求头
        corsConfiguration.addAllowedMethod("*");    // 允许所有的请求方法
        corsConfiguration.addAllowedOriginPattern("*");  // 允许请求来源的域规则
        corsConfiguration.setAllowCredentials(true);  // 是否允许在跨域的情况下传递Cookie
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // 添加路径规则
        return source;
    }


    @Bean
    public XhtLogoutSuccessHandler xhtLogoutSuccessHandler() {
        return new XhtLogoutSuccessHandler();
    }

    @Bean
    public XhtAccessDeniedHandler xhtAccessDeniedHandler() {
        return new XhtAccessDeniedHandler();
    }

    @Bean
    public XhtAuthenticationEntryPoint xhtAuthenticationEntryPoint() {
        return new XhtAuthenticationEntryPoint();
    }

    @Bean
    public XhtLoginFailureHandler xhtLoginFailureHandler() {
        return new XhtLoginFailureHandler();
    }

    @Bean
    public XhtLoginSuccessHandler xhtLoginSuccessHandler(RedisTemplate redisTemplate,JwtTokenUtil jwtTokenUtil ) {
        return new XhtLoginSuccessHandler(redisTemplate,jwtTokenUtil);
    }

    @Bean
    public XhtJwtTokenSecurityFilter xhtJwtTokenSecurityFilter(JwtTokenUtil jwtTokenUtil, XhtUserDetailsService userDetailsService,RedisTemplate redisTemplate){
        return new XhtJwtTokenSecurityFilter(jwtTokenUtil,userDetailsService,redisTemplate);
    }

}


