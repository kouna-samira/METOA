package com.groupe2.METOA.gestionProfilUtiisateur.entity.profil;


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
@Table(name = "profils")
public class Profil {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "profil_id", length = 36, nullable = false)
    private String profilId;

    @Column(nullable = false)
    private String adresse;

    @Column(nullable = false)
    private String sexe;

    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    private String photoUrl;

    @Column(length = 500)
    private String bio;


    @Column(nullable = false)
    private String preferences;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id_user",
            referencedColumnName = "id_user",
            nullable = false
    )
    private User user;
}
