package com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profilePassager;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfilePassagerReqDTO {
    @NotBlank(message = "L'adresse est obligatoire")
    @Size(min = 3, max = 150, message = "Adresse invalide")
    private String adresse;
    @Size(max = 500, message = "La bio ne doit pas dépasser 500 caractères")
    private String bio;
    @NotBlank(message = "Les préférences sont obligatoires")
    @Size(min = 3, max = 200)
    private String preferences;
    @NotBlank(message = "numero urgence obligatoires")
    private String numeroUrgence;
    @NotBlank(message = "entrez le type de bagage habituelle")
    private String typeBagageHabituel;
    @NotBlank(message = "entrz un moyen de payement ")
    private String moyenPaiementPrefere;
    private String frequenceVoyage;
    private String userId; // Pour lier au user existant
}