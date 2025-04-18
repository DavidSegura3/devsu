package com.devsu.demo.ms.account.ms_account.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Account Microservice",
        version = "1.0",
        description = "Documentation for endpoints ms-account")
)
public class OpenApiConfig {
}
