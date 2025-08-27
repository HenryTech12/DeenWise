package org.deenwise.app.apis.configuration;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
       info = @Info(
               contact = @Contact(
                       name = "Fakorode Henry",
                       email = "fakorodehenry@gmail.com"
               ),
               description = "Deen Wise Web Applications RESTfuls APIs",
               termsOfService = "Terms Of Service",
               version = "1.0",
               license = @License(
                       name = "Unilag Student Project"
               )
       ),
        servers = {
               @Server(
                       description = "Local Development",
                       url = "http://localhost:8080/"
               )
        },
        security = {
               @SecurityRequirement(name = "JWT Token")
        }
)
@SecurityScheme(
        name = "JWT Token",
        description = "Jwt Authentication/Authorization Configurations",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfiguration {
}
