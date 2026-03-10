package com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileStandard;

import lombok.Data;

@Data
public class ProfileReqDTO {

    private String adresse;
    private String bio;
    private String preferences;

    private String userId;
}