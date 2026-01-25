package com.marshmallow.robocleaner.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI 3.0 configuration for API documentation.
 */
@Configuration
public class OpenApiConfig {

    @Value("${spring.application.name:Robo Cleaner}")
    private String applicationName;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Robo Cleaner API")
                        .version("2.0.0")
                        .description("""
                                A robotic cleaner service that navigates and cleans oil spills in a sea grid.

                                ## Features
                                - Navigate a robotic cleaner through a 2D grid
                                - Automatically clean oil patches encountered during navigation
                                - Track final position and number of cleaned patches

                                ## Coordinate System
                                - Origin (0, 0) is at the bottom-left corner
                                - X increases going East
                                - Y increases going North
                                """)
                        .contact(new Contact()
                                .name("API Support")
                                .email("support@robocleaner.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Development"),
                        new Server().url("https://api.robocleaner.com").description("Production")
                ));
    }
}
