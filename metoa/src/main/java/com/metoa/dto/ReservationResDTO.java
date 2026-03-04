/*package com.metoa.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReservationResDTO {
    private Long id;
    private Long passagerId;
    private String passagerNom;
    private Long trajetId;
    private String trajetDescription; // "Yaoundé → Douala"
    private int placesReservees;
    private String statut;
    private LocalDateTime dateReservation;
}*/
package com.metoa.dto;

import com.metoa.entity.ReservationStatut;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReservationResDTO {

    @Schema(description = "ID de la réservation", example = "1")
    private Long id;

    @Schema(description = "ID du passager", example = "1")
    private Long passagerId;

    @Schema(description = "Nom complet du passager", example = "Ngono Sarah")
    private String passagerNom;

    @Schema(description = "ID du trajet", example = "1")
    private Long trajetId;

    @Schema(description = "Description du trajet", example = "Yaoundé → Douala")
    private String trajetDescription;

    @Schema(description = "Nombre de places réservées", example = "1")
    private int placesReservees;

    @Schema(description = "Statut de la réservation", allowableValues = {"EN_ATTENTE", "ACCEPTEE", "REFUSEE", "ANNULEE"}, example = "EN_ATTENTE")
    private String statut;

    @Schema(description = "Date de la réservation", example = "2026-03-03T10:30:00")
    private LocalDateTime dateReservation;
}