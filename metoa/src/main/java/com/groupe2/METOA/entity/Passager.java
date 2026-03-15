package com.groupe2.METOA.entity;

import com.metoa.entity.Reservation;
import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Entity
@Table(name = "passager")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Passager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Identifiant unique du passager", example = "1")
    private Long id;

    @Column(name = "nom", nullable = false)
    @Schema(description = "Nom du passager", example = "Ngono")
    private String nom;

    @Column(name = "prenom", nullable = false)
    @Schema(description = "Prénom du passager", example = "Sarah")
    private String prenom;

    @Column(name = "email", nullable = false, unique = true)
    @Schema(description = "Email du passager", example = "sarah@yahoo.com")
    private String email;

    @Column(name = "telephone")
    @Schema(description = "Numéro de téléphone (9 chiffres)", example = "677987654")
    private String telephone;

    @OneToMany(mappedBy = "passager", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Liste des réservations effectuées par le passager")
    private List<Reservation> reservations;
}
