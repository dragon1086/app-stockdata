package com.rocky.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/react-app/**")
                .addResourceLocations("classpath:/react-app/")
                .resourceChain(true);
        
        // 기존의 정적 리소스 경로도 유지
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true);
    }
}