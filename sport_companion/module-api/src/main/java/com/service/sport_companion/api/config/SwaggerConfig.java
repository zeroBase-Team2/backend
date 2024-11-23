package com.service.sport_companion.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .addServersItem(new Server().url("http://localhost:8080/").description("Localhost 전용"))

        // Jwt 도입 후 작성할 예정
//        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
//        .components(new Components()
//            .addSecuritySchemes("bearerAuth",
//                new SecurityScheme()
//                    .name()
//                    .type(Type.APIKEY)
//                    .in(SecurityScheme.In.HEADER)
//                    .bearerFormat("JWT")))
        .info(new Info()
            .title("sport_companion API")
            .version("API v1.0")
            .description("API documentation"));
  }
}
