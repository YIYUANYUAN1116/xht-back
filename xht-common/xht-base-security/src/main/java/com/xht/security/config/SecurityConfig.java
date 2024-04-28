package com.xht.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @author : YIYUANYUAN
 * @date: 2024/4/22  22:44
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        //配置除了登录之外的所有请求都要认证
        httpSecurity.authorizeHttpRequests(requests
                -> requests.requestMatchers(
                "/**").permitAll());



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
}
