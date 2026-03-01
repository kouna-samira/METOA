package com.groupe2.METOA.Dto;

import com.groupe2.METOA.Entity.Trajet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResDto {
    private String idReservation;
    private LocalDate dateReservation;
    private  int nombrePlaces;
    private double prix;
    private Trajet trajet;


}
