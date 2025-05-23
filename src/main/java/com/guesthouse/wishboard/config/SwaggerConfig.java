package com.guesthouse.wishboard.config;


import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@SecurityRequirement(name = "BearerAuth")
public class SwaggerConfig {

// Swagger 접속 주소
// http://localhost:8080/swagger-ui/index.html#/

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Bean
    public OpenAPI customOpenAPI() {
        Server localServer = new Server();
        localServer.setUrl(contextPath);
        localServer.setDescription("Server");


        return new OpenAPI()
                .addServersItem(localServer)
                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("BearerAuth"))
                .info(new Info().title("API 명세서")
                        .version("1.0")
                        .description("Swagger"));

    }

    @Bean
    public GroupedOpenApi customGroupedOpenApi() {
        return GroupedOpenApi.builder().group("api").pathsToMatch("/**").build();
    }
}


