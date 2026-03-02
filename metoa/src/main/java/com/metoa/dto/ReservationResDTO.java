package com.metoa.dto;

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
}