package com.example.urlshortener.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme apiKeyScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("X-Api-Key")
                .description("API key required for protected endpoints");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("ApiKeyAuth", apiKeyScheme))
                .addSecurityItem(new SecurityRequirement().addList("ApiKeyAuth"))
                .info(new Info()
                        .title("URL Shortener Service")
                        .description("Scaffolded API for the URL shortener service")
                        .version("1.0.0"));
    }
}
