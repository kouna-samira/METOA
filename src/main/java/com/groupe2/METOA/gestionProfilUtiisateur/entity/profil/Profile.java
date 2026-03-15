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
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "profile_id", length = 36, nullable = false)
    private String profileId;

    @Column(nullable = false)
    private String adresse;



    private String photoUrl;

    @Column(length = 500)
    private String bio;

    private double noteMoyenne;
    private Integer totalAvis;
    private String badge;

    @Column(nullable = false)
    private String preferences;

    private LocalDate dateCreationProfile;

    private LocalDate dateModificationProfile;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id_user",
            referencedColumnName = "id_user",
            nullable = false
    )
    private User user;
}
