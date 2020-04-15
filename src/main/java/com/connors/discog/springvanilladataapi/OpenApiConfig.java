package com.connors.discog.springvanilladataapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {  "com.connors.discog.springvanilladataapi.controller" })
public class OpenApiConfig {

    @Value("${swagger.docs.title}")
    private String applicationTitle;

    @Value("${swagger.docs.description}")
    private String applicationDescription;

    @Value("${swagger.packages}")
    private String swaggerPackages;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title(applicationTitle).description(applicationDescription));
    }
}
