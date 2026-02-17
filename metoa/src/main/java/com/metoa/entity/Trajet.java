package com.metoa.entity;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
@Entity
@Table(name="trajet")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Trajet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    @Schema(description="Identifiant unique du trajet", example="1")
    private Long id;

    @ManyToOne
    @JoinColumn(name="conducteur_id")
    @Schema(description="Conducteur qui propose le trajet")
    private Conducteur conducteur;

    @ManyToOne
    @JoinColumn(name="vehicule_id")
    @Schema(description="Véhicule utilisé pour le trajet")
    private Vehicule vehicule;

    @ManyToOne
    @JoinColumn(name="ville_depart_id")
    @Schema(description="Ville de départ du trajet")
    private Ville villeDepart;

    @ManyToOne
    @JoinColumn(name="ville_arrivee_id")
    @Schema(description="Ville d'arrivée du trajet")
    private Ville villeArrivee;

    @Column(name="date_depart", nullable=false)
    @Schema(description="Date et heure du départ", example="2026-03-01T08:30")
    private java.time.LocalDateTime dateDepart;

    @Column(name="prix", nullable=false)
    @Schema(description="Prix du trajet en FCFA", example="2500")
    private double prix;

    @Column(name="places_disponibles", nullable=false)
    @Schema(description="Nombre de places disponibles pour le trajet", example="3")
    private int placesDisponibles;

    @Column(name="statut")
    @Schema(description="Statut du trajet", example="EN_ATTENTE")
    private String statut;

    @OneToMany(mappedBy="trajet", cascade=CascadeType.ALL)
    @Schema(description="Liste des réservations pour ce trajet")
    private java.util.List<Reservation> reservations;
}
