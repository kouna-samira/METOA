package com.groupe2.METOA.Dto;

import com.groupe2.METOA.Entity.Trajet;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationReqDto {
    @NotNull(message = "Date obligatoire")
    private LocalDate dateReservation;
    @Positive(message = "Le nombre de places doit être positif")
    private int nombrePlaces;
    @Positive(message = "Le prix doit être positif")
    private double prix;
    @NotNull(message = "ID du trajet obligatoire")
    private String idTrajet;


}
