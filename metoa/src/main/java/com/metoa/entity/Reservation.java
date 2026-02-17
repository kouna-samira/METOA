package com.metoa.entity;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
@Entity
@Table(name="reservation")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    @Schema(description="Identifiant unique de la réservation", example="1")
    private Long id;

    @ManyToOne
    @JoinColumn(name="passager_id")
    @Schema(description="Passager ayant effectué la réservation")
    private Passager passager;

    @ManyToOne
    @JoinColumn(name="trajet_id")
    @Schema(description="Trajet réservé")
    private Trajet trajet;

    @Column(name="places_reservees", nullable=false)
    @Schema(description="Nombre de places réservées", example="2")
    private int nombrePlacesReservees;

    @Column(name="statut")
    @Schema(description="Statut de la réservation", example="EN_ATTENTE")
    private String statut;

    @Column(name="date_reservation", nullable=false)
    @Schema(description="Date et heure de la réservation", example="2026-02-17T12:00")
    private java.time.LocalDateTime dateReservation;
}
