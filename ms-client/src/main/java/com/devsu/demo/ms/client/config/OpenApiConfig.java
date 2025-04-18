package com.devsu.demo.ms.client.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Client Microservice",
        version = "1.0",
        description = "Documentation for endpoints ms-client")
)
public class OpenApiConfig {
}
