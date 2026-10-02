package com.ecommerce.comments.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${app.api.version:v1}")
    private String apiVersion;

    @Value("${app.api.title:Comments API}")
    private String apiTitle;

    @Value("${app.api.description:Sistema de gestión de comentarios para productos de e-commerce}")
    private String apiDescription;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title(apiTitle)
                .version(apiVersion)
                .description(apiDescription)
                .contact(new Contact()
                    .name("Equipo de Desarrollo")
                    .email("dev@ecommerce.com")
                    .url("https://ecommerce.com")
                )
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0.html")
                )
            )
            .servers(List.of(
                new Server()
                    .url("http://localhost:" + serverPort)
                    .description("Servidor de desarrollo local"),
                new Server()
                    .url("/api")
                    .description("Servidor de producción")
            ))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new Components()
                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .description("Token JWT para autenticación")
                )
            );
    }
}