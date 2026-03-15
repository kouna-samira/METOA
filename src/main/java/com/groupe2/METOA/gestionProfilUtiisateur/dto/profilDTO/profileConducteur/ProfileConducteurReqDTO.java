package com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileConducteur;


import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.TyperDocument;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileConducteurReqDTO {
    @NotBlank(message = "L'adresse est obligatoire")
    @Size(min = 3, max = 150, message = "Adresse invalide")
    private String adresse;
    private String photoUrl;
    @Size(max = 500, message = "La bio ne doit pas dépasser 500 caractères")
    private String bio;
    @NotBlank(message = "Les préférences sont obligatoires")
    @Size(min = 3, max = 200)
    private String preferences;

    private Boolean actif;
    private Double noteMoyenne;
    private Integer nombreTrajetsEffectues;

    private String vehicule;
    private String document;
    private TyperDocument typeDocument;

    private Double tauxAcceptation;

    private String userId; // important pour l'association
}