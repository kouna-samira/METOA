package com.groupe2.METOA.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationReqDTO {

    @NotNull
    @Schema(description = "ID du passager", example = "1")
    private Long passagerId;

    @NotNull
    @Schema(description = "ID du trajet", example = "1")
    private Long trajetId;

    @Min(1)
    @Schema(description = "Nombre de places réservées", example = "1")
    private int placesReservees;
}