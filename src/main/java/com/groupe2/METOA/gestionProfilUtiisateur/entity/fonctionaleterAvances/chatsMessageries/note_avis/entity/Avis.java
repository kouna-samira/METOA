package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.note_avis.entity;

import com.groupe2.METOA.Entity.Trajet;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "avis")
public class Avis {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String avisId;

    @Column(nullable = false)
    private int note;

    @Column(length = 500)
    private String commentaire;

    private LocalDateTime dateAvis;

    @ManyToOne
    @JoinColumn(name = "auteur_id")
    private User auteur;

    @ManyToOne
    @JoinColumn(name = "cible_id")
    private User cible;

    @ManyToOne
    @JoinColumn(name = "trajet_id")
    private Trajet trajet;

    private Boolean visible;

}