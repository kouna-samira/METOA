package com.groupe2.METOA.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.groupe2.METOA.Dto.ClientReqDto;
import com.groupe2.METOA.Enum.StatutReservation;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String idReservation;
    private LocalDate dateReservation;
    private  int nombrePlaces;
    private double prix;
    @Enumerated(EnumType.STRING)
    private StatutReservation statut;

    @ManyToOne
    private Trajet trajet;
    @ManyToOne

    private Client client;




}
