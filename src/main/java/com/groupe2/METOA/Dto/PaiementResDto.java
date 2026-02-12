package com.groupe2.METOA.Dto;

import com.groupe2.METOA.Entity.Reservation;

import java.time.LocalDate;

public class PaiementResDto {
    private String idPaiement;
    private LocalDate datePaiement;
    private  Double montant;
    private String statut;//refuser ou valide
    private String methode;
    private Reservation reservation;

    public PaiementResDto(String idPaiement, String methode, String statut, Double montant, LocalDate datePaiement) {
        this.idPaiement = idPaiement;
        this.methode = methode;
        this.statut = statut;
        this.montant = montant;
        this.datePaiement = datePaiement;
    }

    public PaiementResDto(String idPaiement, LocalDate datePaiement, Double montant, String statut, String methode, Reservation reservation) {
        this.idPaiement = idPaiement;
        this.datePaiement = datePaiement;
        this.montant = montant;
        this.statut = statut;
        this.methode = methode;
        this.reservation = reservation;
    }

    public String getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(String idPaiement) {
        this.idPaiement = idPaiement;
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
