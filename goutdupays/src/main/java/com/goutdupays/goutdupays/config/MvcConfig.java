package com.goutdupays.goutdupays.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        Dotenv dotenv = Dotenv.load();
        String corsUrl;

        String env = dotenv.get("ENV");
        logger.info("ENV ENVIRONEMENT: {}", env);
        if ("PROD".equalsIgnoreCase(env)) {
            corsUrl = dotenv.get("CORS_URL_PROD");
        } else {
            corsUrl = dotenv.get("CORS_URL_LOCAL");
        }

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(corsUrl);
                logger.info("Configuring CORS for /api/** with allowed origins: {}", corsUrl);
            }
        };
    }
}
