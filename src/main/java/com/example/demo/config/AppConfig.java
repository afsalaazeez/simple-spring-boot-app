package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Application configuration class.
 * Demonstrates the use of @Configuration annotation.
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {

    /**
     * Configure CORS for the application.
     * Allows cross-origin requests from any origin for development purposes.
     * In production, you should restrict this to specific origins.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*")
                .maxAge(3600);
    }
}
