package com.metoa.entity;

import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
@Entity
@Table(name="passager")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Passager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    @Schema(description="Identifiant unique du passager", example="1")
    private Long id;

    @Column(name="nom", nullable=false)
    @Schema(description="Nom du passager", example="Ngono")
    private String nom;

    @Column(name="prenom", nullable=false)
    @Schema(description="Prénom du passager", example="Sarah")
    private String prenom;

    @Column(name="email", nullable=false, unique=true)
    @Schema(description="Email du passager", example="sarah@yahoo.com")
    private String email;

    @Column(name="telephone")
    @Schema(description="Numéro de téléphone ", example="677987654")
    private String telephone;

    @OneToMany(mappedBy="passager", cascade=CascadeType.ALL)
    @Schema(description="Liste des réservations effectuées par le passager")
    private java.util.List<Reservation> reservations;
}

