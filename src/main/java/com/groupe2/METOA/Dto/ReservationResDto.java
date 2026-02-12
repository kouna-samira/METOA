package com.groupe2.METOA.Dto;

import com.groupe2.METOA.Entity.Trajet;

import java.time.LocalDate;

public class ReservationResDto {
    private String idReservation;
    private LocalDate dateReservation;
    private  int nombrePlaces;
    private double prix;
    private Trajet trajet;

    public ReservationResDto(String idReservation, double prix, int nombrePlaces, LocalDate dateReservation) {
        this.idReservation = idReservation;
        this.prix = prix;
        this.nombrePlaces = nombrePlaces;
        this.dateReservation = dateReservation;
    }

    public ReservationResDto(String idReservation, LocalDate dateReservation, int nombrePlaces, double prix, Trajet trajet) {
        this.idReservation = idReservation;
        this.dateReservation = dateReservation;
        this.nombrePlaces = nombrePlaces;
        this.prix = prix;
        this.trajet = trajet;
    }

    public String getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(String idReservation) {
        this.idReservation = idReservation;
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
