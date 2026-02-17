package com.metoa.entity;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name="ville")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class Ville {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    @Schema(description="Identifiant unique de la ville", example="1")
    private Long id;

    @Column(name="nom", nullable=false)
    @Schema(description="Nom de la ville", example="Yaoundé")
    private String nom;

    @Column(name="region")
    @Schema(description="Région où se situe la ville", example="Centre")
    private String region;

    @Column(name="latitude", nullable=false)
    @Schema(description="Latitude de la ville pour la géolocalisation", example="3.8480")
    private double latitude;

    @Column(name="longitude", nullable=false)
    @Schema(description="Longitude de la ville pour la géolocalisation", example="11.5021")
    private double longitude;
}
