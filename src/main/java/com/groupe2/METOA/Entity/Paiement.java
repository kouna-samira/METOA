package com.groupe2.METOA.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Paiement {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String idPaiement;
    private LocalDate datePaiement;
    private  Double montant;
    private String statut;//refuser ou valide
    private String methode;

    public Paiement() {
    }

    public Paiement(LocalDate datePaiement, Double montant, String statut, String methode) {
        this.datePaiement = datePaiement;
        this.montant = montant;
        this.statut = statut;
        this.methode = methode;
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
}
