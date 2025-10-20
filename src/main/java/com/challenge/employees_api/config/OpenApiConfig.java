package com.challenge.employees_api.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI employeesOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Employees API")
            .description("Rest Service for Employee management")
            .version("v1"))
        .servers(List.of(new Server().url("/")));
  }
}
