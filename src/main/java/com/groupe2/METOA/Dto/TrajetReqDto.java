package com.groupe2.METOA.Dto;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public class TrajetReqDto {
    private LocalDate dateTrajet;
    @NotEmpty(message="fill this field")
    private String depart;
    @NotEmpty(message="fill this field")
    private String destination;
    private double distance;

    public TrajetReqDto() {
    }

    public TrajetReqDto(LocalDate dateTrajet, String depart, String destination, double distance) {
        this.dateTrajet = dateTrajet;
        this.depart = depart;
        this.destination = destination;
        this.distance = distance;
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
