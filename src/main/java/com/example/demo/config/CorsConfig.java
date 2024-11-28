package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // 允许跨域的源
        config.addAllowedOrigin("http://127.0.0.1:5500");
        config.addAllowedOrigin("http://localhost:5500");
        
        // 允许跨域的头
        config.addAllowedHeader("*");
        
        // 允许跨域的方法
        config.addAllowedMethod("*");
        
        // 允许携带凭证
        config.setAllowCredentials(true);
        
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
} 