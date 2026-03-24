package com.groupe2.METOA.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ConducteurReqDTO {

    @NotBlank(message = "Le nom est obligatoire")
    @Schema(description = "Nom du conducteur", example = "Bilong")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    @Schema(description = "Prénom du conducteur", example = "Pradel")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @Schema(description = "Email du conducteur", example = "pradel@gmail.com")
    private String email;

    @Pattern(regexp = "^[0-9]{9}$", message = "Le téléphone doit comporter 9 chiffres")
    @Schema(description = "Numéro de téléphone (9 chiffres)", example = "690123456")
    private String telephone;
}
