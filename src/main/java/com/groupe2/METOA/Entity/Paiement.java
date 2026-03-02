package com.groupe2.METOA.Entity;


import com.groupe2.METOA.Enum.StatutPaiement;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Paiement {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String idPaiement;
    private LocalDate datePaiement;
    private  Double montant;
    @Enumerated(EnumType.STRING)
    private StatutPaiement statut;//refuser ou valide
    private String methode;

    @OneToOne
    @JoinColumn(name = "idReservation")
    private Reservation reservation;



}
