package com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileStandard;


import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileResDTO {

    private String profileId;
    private String adresse;
    private String photoUrl;
    private String bio;
    private String preferences;
    private LocalDate dateCreationProfile;
    private LocalDate dateModificationProfile;
}
