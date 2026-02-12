package com.groupe2.METOA.Dto;

import com.groupe2.METOA.Entity.Reservation;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public class PaiementReqDto {
    private LocalDate datePaiement;
    private  Double montant;
    @NotEmpty(message = "Fill this field")
    private String statut;//refuser ou valide
    @NotEmpty(message = "Fill this field")
    private String methode;
    private Reservation reservation;

    public PaiementReqDto() {
    }

    public PaiementReqDto(LocalDate datePaiement, Double montant, String statut, String methode) {
        this.datePaiement = datePaiement;
        this.montant = montant;
        this.statut = statut;
        this.methode = methode;
    }

    public PaiementReqDto(LocalDate datePaiement, Double montant, String statut, String methode, Reservation reservation) {
        this.datePaiement = datePaiement;
        this.montant = montant;
        this.statut = statut;
        this.methode = methode;
        this.reservation = reservation;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getMethode() {
        return methode;
    }

    public void setMethode(String methode) {
        this.methode = methode;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
