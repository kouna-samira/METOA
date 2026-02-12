package com.groupe2.METOA.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String idReservation;
    private LocalDate dateReservation;
    private  int nombrePlaces;
    private double prix;

    public Reservation() {
    }

    public Reservation(LocalDate dateReservation, int nombrePlaces, double prix) {
        this.dateReservation = dateReservation;
        this.nombrePlaces = nombrePlaces;
        this.prix = prix;
    }

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
