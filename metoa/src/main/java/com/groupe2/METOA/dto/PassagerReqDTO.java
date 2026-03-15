package com.groupe2.METOA.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PassagerReqDTO {

    @NotBlank
    @Schema(description = "Nom du passager", example = "Ngono")
    private String nom;

    @NotBlank
    @Schema(description = "Prénom du passager", example = "Sarah")
    private String prenom;

    @NotBlank
    @Email(message = "Format d'email invalide")
    @Schema(description = "Email du passager", example = "sarah@yahoo.com")
    private String email;

    @Pattern(regexp = "^[0-9]{9}$", message = "Le téléphone doit comporter 9 chiffres")
    @Schema(description = "Numéro de téléphone (9 chiffres)", example = "677987654")
    private String telephone;
}