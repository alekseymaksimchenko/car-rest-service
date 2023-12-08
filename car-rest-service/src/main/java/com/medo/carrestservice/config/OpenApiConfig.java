package com.medo.carrestservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;



@OpenAPIDefinition(
    info = @Info(
        contact = @Contact(
            name = "Aleksey",
            email = "aleksey.m@gmail.com"
            ),
        description = "Open api documentation for Spring boot rest aplication",
        title = "Car-Rest-Service-Application"
        ),
    servers = @Server(
        description = "local dev env",
        url = "http://localhost:8080"),
    security = @SecurityRequirement(name = "bearerAuth") 
    )

@SecurityScheme( 
    name = "bearerAuth",
    description = "JWT auth",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
    )
  
public class OpenApiConfig {
  
}
