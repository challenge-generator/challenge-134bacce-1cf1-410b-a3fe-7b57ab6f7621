package com.ecommerce.comments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableConfigurationProperties
@EnableAsync
@EnableRetry
public class CommentsApplication {

    private final ApplicationProperties applicationProperties;

    public CommentsApplication(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
        validateProperties();
    }

    public static void main(String[] args) {
        SpringApplication.run(CommentsApplication.class, args);
    }

    private void validateProperties() {
        if (applicationProperties.getAllowedOrigins() == null || applicationProperties.getAllowedOrigins().isEmpty()) {
            throw new IllegalStateException("La propiedad 'app.allowed-origins' debe estar configurada");
        }
        if (applicationProperties.getStorage().getMaxContentLength() <= 0) {
            throw new IllegalStateException("La propiedad 'app.storage.max-content-length' debe ser mayor que 0");
        }
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(applicationProperties.getAllowedOrigins().toArray(new String[0]))
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    @Configuration
    public static class ApplicationProperties {
        private java.util.List<String> allowedOrigins;
        private StorageProperties storage;

        public java.util.List<String> getAllowedOrigins() {
            return allowedOrigins;
        }

        public void setAllowedOrigins(java.util.List<String> allowedOrigins) {
            this.allowedOrigins = allowedOrigins;
        }

        public StorageProperties getStorage() {
            return storage;
        }

        public void setStorage(StorageProperties storage) {
            this.storage = storage;
        }

        public static class StorageProperties {
            private int maxContentLength;
            private int maxTitleLength;

            public int getMaxContentLength() {
                return maxContentLength;
            }

            public void setMaxContentLength(int maxContentLength) {
                this.maxContentLength = maxContentLength;
            }

            public int getMaxTitleLength() {
                return maxTitleLength;
            }

            public void setMaxTitleLength(int maxTitleLength) {
                this.maxTitleLength = maxTitleLength;
            }
        }
    }
}