package com.groupe2.METOA.Config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "METOA - Gestion de reservation",
                version = "v1.0",
                description = "application de covoiturage",
                contact = @Contact(
                        name = "Groupe 2 - METOA Project",
                        email = "contact@metoa.com"

                )

        )
)

public class OpenApiConfig {
}
