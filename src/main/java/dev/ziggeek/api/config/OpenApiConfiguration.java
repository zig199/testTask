package dev.ziggeek.api.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;

import java.util.List;


@OpenAPIDefinition(
        info = @Info(
                title = "ZigGeek-Api",
                description = "MeshGroup test task for Java-developer", version = "1.0.0",
                contact = @Contact(
                        name = "Abubakar Muradov",
                        email = "muradov.abubakar999@gmail.com",
                        url = "https://t.me/ziggeek"
                )
        )
)
public class OpenApiConfiguration {
}
