package com.metoa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VilleReqDTO {

    @NotBlank
    @Schema(description = "Nom de la ville", example = "Yaoundé")
    private String nom;

    @Schema(description = "Région", example = "Centre")
    private String region;

    @Schema(description = "Latitude", example = "3.8480")
    private double latitude;

    @Schema(description = "Longitude", example = "11.5021")
    private double longitude;
}