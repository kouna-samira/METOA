package com.groupe2.METOA.Entity;

<<<<<<< HEAD
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
=======
import jakarta.persistence.*;
>>>>>>> cc79e66 (fin des entites)

import java.time.LocalDate;

@Entity
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String idReservation;
    private LocalDate dateReservation;
    private  int nombrePlaces;
    private double prix;
<<<<<<< HEAD
=======
    @ManyToOne
    private Trajet trajet;
>>>>>>> cc79e66 (fin des entites)

    public Reservation() {
    }

<<<<<<< HEAD
=======
    public Reservation(String idReservation, LocalDate dateReservation, int nombrePlaces, double prix, Trajet trajet) {
        this.idReservation = idReservation;
        this.dateReservation = dateReservation;
        this.nombrePlaces = nombrePlaces;
        this.prix = prix;
        this.trajet = trajet;
    }

>>>>>>> cc79e66 (fin des entites)
    public Reservation(LocalDate dateReservation, int nombrePlaces, double prix) {
        this.dateReservation = dateReservation;
        this.nombrePlaces = nombrePlaces;
        this.prix = prix;
    }

<<<<<<< HEAD
=======
    public String getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(String idReservation) {
        this.idReservation = idReservation;
    }

    public Trajet getTrajet() {
        return trajet;
    }

    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }

>>>>>>> cc79e66 (fin des entites)
    public LocalDate getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(LocalDate dateReservation) {
        this.dateReservation = dateReservation;
    }

    public int getNombrePlaces() {
        return nombrePlaces;
    }

    public void setNombrePlaces(int nombrePlaces) {
        this.nombrePlaces = nombrePlaces;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
