package com.metoa.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "LOGONEDIGITAL TEKHUB ACADEMY",
                        email = "contact@logonedigital.com",
                        url = "https://logonedigital.com"
                ),
                title = "METOA",
                description = "METOA-COVOITURAGE-GROUPE-2",
                termsOfService = "&copy; LOGONEDIGITAL",
                version = "v1"
        )
)
public class OpenApiConfig {
}
