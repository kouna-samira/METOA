package com.groupe2.METOA.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TrajetReqDTO {

    @NotNull
    @Schema(description = "ID du conducteur", example = "1")
    private Long conducteurId;

    @NotNull
    @Schema(description = "ID du véhicule", example = "1")
    private Long vehiculeId;

    @NotNull
    @Schema(description = "ID de la ville de départ", example = "1")
    private Long villeDepartId;

    @NotNull
    @Schema(description = "ID de la ville d'arrivée", example = "2")
    private Long villeArriveeId;

    @NotNull
    @Future(message = "La date de départ doit être dans le futur")
    @Schema(description = "Date et heure de départ", example = "2026-03-15T08:00:00")
    private LocalDateTime dateDepart;

    @Min(1)
    @Schema(description = "Places disponibles", example = "3")
    private int placesDisponibles;

    @Min(0)
    @Schema(description = "Prix par place (FCFA)", example = "2500")
    private double prix;

    // Coordonnées GPS optionnelles (si on veut surcharger celles de la ville)
    @Schema(description = "Latitude du point de départ (optionnel)", example = "3.8480")
    private Double latitudeDepart;
    @Schema(description = "Longitude du point de départ (optionnel)", example = "11.5021")
    private Double longitudeDepart;
    @Schema(description = "Latitude du point d'arrivée (optionnel)", example = "4.0500")
    private Double latitudeArrivee;
    @Schema(description = "Longitude du point d'arrivée (optionnel)", example = "9.7000")
    private Double longitudeArrivee;
}
