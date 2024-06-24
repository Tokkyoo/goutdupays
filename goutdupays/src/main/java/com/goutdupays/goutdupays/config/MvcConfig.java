package com.goutdupays.goutdupays.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(MvcConfig.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String baseDir = "file:/external/uploads/";
        registry.addResourceHandler("/uploads/**").addResourceLocations(baseDir);
        logger.info("Configuring resource handler for /uploads/** to serve from {}", baseDir);
    }
}

