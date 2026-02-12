package com.groupe2.METOA.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Trajet {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private  String idTrajet;
    private LocalDate dateTrajet;
    private String depart;
    private String destination;
    private double distance;

    public Trajet() {
    }

    public Trajet(LocalDate dateTrajet, String depart, String destination,  double distance) {
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
