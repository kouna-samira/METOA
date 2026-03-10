package com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileConducteur;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class ProfileConducteurResDTO {

        private String profileConducteurId;

        private String adresse;
        private String photoUrl;
        private String bio;
        private String preferences;

        private Boolean actif;
        private Double noteMoyenne;
        private Integer nombreTrajetsEffectues;

        private String vehicule;
        private String document;

        private LocalDate dateCreationProfile;
        private LocalDate dateModificationProfile;

        private Double tauxAcceptation;

        private String userId; // pour exposer l'id du user uniquement
    }
