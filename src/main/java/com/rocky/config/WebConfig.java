package com.rocky.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 기존 리소스 (JS, CSS 등)
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("classpath:/static/");

        // React 앱
        registry.addResourceHandler("/app/**")
                .addResourceLocations("classpath:/react-app/")
                .resourceChain(true);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // SPA 라우팅을 위한 설정
        registry.addViewController("/app/**").setViewName("forward:/app/index.html");
    }
}