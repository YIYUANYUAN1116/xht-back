package com.xht.security.config;


import com.xht.security.interceptor.XhtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : YIYUANYUAN
 * @date: 2024/4/24  22:09
 */
@Configuration
public class XhtMvcConfig implements WebMvcConfigurer {
//    @Autowired
//    private XhtInterceptor xhtInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 对swagger的请求不进行拦截
        String[] excludePatterns = new String[]{"/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**",
                "/api", "/api-docs", "/api-docs/**", "/doc.html/**", "/doc.html#/**", "/static/**","/swagger-ui.html",
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/v3/api-docs/**"};
        registry.addInterceptor(xhtInterceptor()).addPathPatterns("/**").excludePathPatterns(excludePatterns);
    }

    @Bean
    public XhtInterceptor xhtInterceptor(){
        return new XhtInterceptor();
    }
}
