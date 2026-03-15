package com.groupe2.METOA.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class VehiculeReqDTO {

    @NotBlank
    @Schema(description = "Marque", example = "Toyota")
    private String marque;

    @NotBlank
    @Schema(description = "Modèle", example = "Corolla")
    private String modele;

    @Schema(description = "Couleur", example = "Noir")
    private String couleur;

    @NotBlank
    @Pattern(regexp = "^[A-Z0-9\\s-]+$", message = "Immatriculation invalide")
    @Schema(description = "Immatriculation", example = "CE 254 AL")
    private String immatriculation;

    @Min(1)
    @Schema(description = "Nombre de places", example = "4")
    private int nombrePlaces;

    @Schema(description = "ID du conducteur propriétaire")
    private Long conducteurId;
}