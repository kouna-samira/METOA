package com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO;


import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.ProfilReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
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
public class UserReqDTO {

    @NotEmpty(message = "entrez votre nom !")
    private String nom;
    @NotEmpty(message = "entrez votre prenom !")
    private String prenom;
    @NotEmpty(message = "entrez votre tel ")
    private String telephone;
    @Email(message = "votre email est erronee !")
    @NotEmpty(message = "entrez votre email !")
    private String email;
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String passe;
    @NotNull(message = "choisisez vorte role: (CONDUCTEUR/ PASSAGER) ")
    @Schema(description = "Rôle de l'utilisateur")
    private Role role;
    private ProfilReqDTO profil;

}
