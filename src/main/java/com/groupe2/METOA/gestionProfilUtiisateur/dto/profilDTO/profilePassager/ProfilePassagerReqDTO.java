package com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profilePassager;
import lombok.Data;

@Data
public class ProfilePassagerReqDTO {
    private String adresse;
    private String bio;
    private String preferences;
    private String numeroUrgence;
    private String typeBagageHabituel;
    private String moyenPaiementPrefere;
    private String frequenceVoyage;
    private String userId; // Pour lier au user existant
}