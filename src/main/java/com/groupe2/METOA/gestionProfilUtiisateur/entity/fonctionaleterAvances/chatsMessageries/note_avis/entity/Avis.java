package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.note_avis.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"auteurId", "trajetId"}
        )
)
public class Avis {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String auteurId;

    private String cibleId;

    private String trajetId;

    @Column(nullable = false)
    private double note; // 0 à 5

    @Column(length = 1500)
    private String commentaire;

    private boolean modifiable;

    private boolean signale;

    private LocalDateTime dateCreation;

    private LocalDateTime dateModification;
}