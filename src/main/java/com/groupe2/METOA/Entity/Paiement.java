package com.groupe2.METOA.Entity;

<<<<<<< HEAD
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
=======
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
>>>>>>> cc79e66 (fin des entites)

@Entity
public class Paiement {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String idPaiement;
    private LocalDate datePaiement;
    private  Double montant;
    private String statut;//refuser ou valide
    private String methode;
<<<<<<< HEAD
=======
    @OneToOne
    private Reservation reservation;
>>>>>>> cc79e66 (fin des entites)

    public Paiement() {
    }

<<<<<<< HEAD
=======
    public Paiement(String idPaiement, LocalDate datePaiement, Double montant, String statut, String methode, Reservation reservation) {
        this.idPaiement = idPaiement;
        this.datePaiement = datePaiement;
        this.montant = montant;
        this.statut = statut;
        this.methode = methode;
        this.reservation = reservation;
    }

>>>>>>> cc79e66 (fin des entites)
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

<<<<<<< HEAD
=======
    public String getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(String idPaiement) {
        this.idPaiement = idPaiement;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

>>>>>>> cc79e66 (fin des entites)
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
