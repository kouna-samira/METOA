package com.groupe2.METOA.gestionProfilUtiisateur.entity.profil;


import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.entity.Badge;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@Entity
@Table(name = "profile_conduteur")
public class ProfileConducteur {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "profileCouducteur_id", length = 36, nullable = false)
    private String profileConducteurId;

    @Column(nullable = false)
    private String adresse;


    private double noteMoyenne;
    private Integer totalAvis;
    private Badge badge;

    private String photoUrl;

    @Column(length = 500)
    private String bio;


    @Column(nullable = false)
    private String preferences;

    private Boolean actif;


    private Integer nombreTrajetsEffectues;

    private String vehicule;
    private String documentUrl;
    private String documentName;



    private int nombreAvis;

    @Enumerated(EnumType.STRING)
    private TyperDocument documentType;
    private LocalDate dateCreationProfile;

    // Statistiques
    private Double tauxAcceptation;

    private LocalDate dateModificationProfile;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id_user",
            referencedColumnName = "id_user",
            nullable = false
    )
    private User user;
}
