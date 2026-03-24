package com.groupe2.METOA.entity;

import com.metoa.entity.Conducteur;
import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "vehicule")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Identifiant unique du véhicule", example = "1")
    private Long id;

    @Column(name = "marque", nullable = false)
    @Schema(description = "Marque du véhicule", example = "Toyota")
    private String marque;

    @Column(name = "modele", nullable = false)
    @Schema(description = "Modèle du véhicule", example = "Corolla")
    private String modele;

    @Column(name = "couleur")
    @Schema(description = "Couleur du véhicule", example = "Noir")
    private String couleur;

    @Column(name = "immatriculation", nullable = false, unique = true)
    @Schema(description = "Immatriculation du véhicule", example = "CE 254 AL")
    private String immatriculation;

    @Column(name = "nombre_places", nullable = false)
    @Schema(description = "Nombre de places dans le véhicule", example = "4")
    private int nombrePlaces;

    @ManyToOne
    @JoinColumn(name = "conducteur_id")
    @Schema(description = "Conducteur propriétaire du véhicule")
    private Conducteur conducteur;
}