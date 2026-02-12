package com.groupe2.METOA.Dto;

import com.groupe2.METOA.Entity.Trajet;

import java.time.LocalDate;

public class ReservationReqDto {
    private LocalDate dateReservation;
    private  int nombrePlaces;
    private double prix;
    private Trajet trajet;

    public ReservationReqDto() {
    }

    public ReservationReqDto( double prix, int nombrePlaces, LocalDate dateReservation) {

        this.prix = prix;
        this.nombrePlaces = nombrePlaces;
        this.dateReservation = dateReservation;
    }

    public ReservationReqDto(LocalDate dateReservation, int nombrePlaces, double prix, Trajet trajet) {
        this.dateReservation = dateReservation;
        this.nombrePlaces = nombrePlaces;
        this.prix = prix;
        this.trajet = trajet;
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

    public Trajet getTrajet() {
        return trajet;
    }

    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }
}
