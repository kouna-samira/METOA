package com.groupe2.METOA.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Trajet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idTrajet;
    private LocalDate dateTrajet;
    private String depart;
    private String destination;
    private double distance;
    private  double prix;

}

