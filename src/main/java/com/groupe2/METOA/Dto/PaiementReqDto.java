package com.groupe2.METOA.Dto;

import com.groupe2.METOA.Entity.Reservation;
import com.groupe2.METOA.Enum.StatutPaiement;
import jakarta.validation.constraints.NotEmpty;
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
public class PaiementReqDto {
    @NotNull(message = "Date obligatoire")
    private LocalDate datePaiement;
    @NotNull(message = "Montant obligatoire")
    @Positive(message = "Le montant doit être positif")
    private Double montant;
    @NotEmpty(message = "Status obligatoire")
    private StatutPaiement statut;//refuser ou valide
    @NotEmpty(message = "Methode obligatoire")
    private String methode;
    @NotEmpty(message = "Reservation ID obligatoire")
    private String idreservation;

}