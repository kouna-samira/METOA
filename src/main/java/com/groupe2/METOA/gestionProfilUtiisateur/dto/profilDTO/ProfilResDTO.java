package com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class ProfilResDTO {
    private String profiId;
    private String adresse;
    private String sexe;
    private String photoUrl;
    private String dateNaissance;
    private String bio;
    private String preferences;
}
