package com.groupe2.METOA.entity;

import com.metoa.entity.Trajet;
import com.metoa.entity.Vehicule;
import jakarta.persistence.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Entity
@Table(name = "conducteur")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conducteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Identifiant unique du conducteur", example = "1")
    private Long id;

    @Column(name = "nom", nullable = false)
    @Schema(description = "Nom du conducteur", example = "Bilong")
    private String nom;

    @Column(name = "prenom", nullable = false)
    @Schema(description = "Prénom du conducteur", example = "Pradel")
    private String prenom;

    @Column(name = "email", nullable = false, unique = true)
    @Schema(description = "Email du conducteur", example = "pradel@gmail.com")
    private String email;

    @Column(name = "telephone")
    @Schema(description = "Numéro de téléphone au format Cameroun (9 chiffres)", example = "690123456")
    private String telephone;

    @OneToMany(mappedBy = "conducteur", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Liste des trajets proposés par le conducteur")
    private List<Trajet> trajets;

    @OneToMany(mappedBy = "conducteur", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Liste des véhicules du conducteur")
    private List<Vehicule> vehicules;
}