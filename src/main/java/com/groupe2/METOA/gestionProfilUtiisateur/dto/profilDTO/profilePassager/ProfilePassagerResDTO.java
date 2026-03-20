package com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profilePassager;

import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserResDTO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfilePassagerResDTO {
    private String profilepassagerId;
    private String adresse;
    private String photoUrl;
    private String bio;
    private String preferences;
    private String numeroUrgence;
    private Integer nombreVoyagesEffectues;
    private Double noteMoyenne;
    private Boolean profilVerifie;
    private Boolean actif;
    private String typeBagageHabituel;
    private String moyenPaiementPrefere;
    private String frequenceVoyage;
    private LocalDate dateCreationProfile;
    private LocalDate dateModificationProfile;
    private UserResDTO userResDTO;
}