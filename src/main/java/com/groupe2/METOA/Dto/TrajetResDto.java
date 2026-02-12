package com.groupe2.METOA.Dto;

import java.time.LocalDate;

public class TrajetResDto {
    private  String idTrajet;
    private LocalDate dateTrajet;
    private String depart;
    private String destination;
    private double distance;

    public TrajetResDto() {
    }

    public TrajetResDto(String idTrajet, LocalDate dateTrajet, String depart, String destination, double distance) {
        this.idTrajet = idTrajet;
        this.dateTrajet = dateTrajet;
        this.depart = depart;
        this.destination = destination;
        this.distance = distance;
    }

    public String getIdTrajet() {
        return idTrajet;
    }

    public void setIdTrajet(String idTrajet) {
        this.idTrajet = idTrajet;
    }

    public LocalDate getDateTrajet() {
        return dateTrajet;
    }

    public void setDateTrajet(LocalDate dateTrajet) {
        this.dateTrajet = dateTrajet;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
