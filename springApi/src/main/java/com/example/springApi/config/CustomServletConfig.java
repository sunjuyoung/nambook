package com.example.springApi.config;

import com.example.springApi.controller.formatter.LocalDateFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomServletConfig implements WebMvcConfigurer {


    //스프링MVC 동작 과정에서 사용될 수 있도록 설정을 추가해 주어야 합니다.
//WebMvcConfigurer 인터페이스를 구현하고 addFormatters()를 오버라이딩하여
//FormatterRegistry에 LocalDateFormatter를 추가합니다.
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new LocalDateFormatter());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
                .maxAge(300)
                .allowedHeaders("Authorization", "Content-Type", "Cache-Control");
    }
}
