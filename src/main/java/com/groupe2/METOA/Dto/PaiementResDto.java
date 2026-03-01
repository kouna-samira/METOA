package com.groupe2.METOA.Dto;

import com.groupe2.METOA.Entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaiementResDto {
    private String idPaiement;
    private LocalDate datePaiement;
    private  Double montant;
    private String statut;//refuser ou valide
    private String methode;
    private String idReservation;
}
