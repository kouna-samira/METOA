package com.groupe2.METOA.gestionProfilUtiisateur.entity.user;


import com.groupe2.METOA.gestionProfilUtiisateur.entity.Role;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.historiqueTrajet.HistoriqueTrajet;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.ProfileConducteur;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.ProfilePassager;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_user_nom", columnList = "nom"),
                @Index(name = "idx_user_prenom", columnList = "prenom"),
                @Index(name = "idx_user_email", columnList = "email"),
                @Index(name = "idx_user_ville", columnList = "ville")
        }
)
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_user", length = 36, nullable = false)
    private String idUser;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    private String lieuNaissance;

    @Column(nullable = false)
    private Sexes sexe;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String telephone;

    @Column(unique = true)
    private String userName;

    @Column(nullable = false)
    private String passe;

    private String ville;

    @Column(name = "visibilite_telephone")
    private boolean visibiliteTelephone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusUser statusUser;

    @Column(name = "date_inscription", nullable = false)
    private LocalDateTime dateInscription;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<HistoriqueTrajet> historiquesTrajets;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ProfileConducteur profileConducteur;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ProfilePassager profilePassager;
}
