package com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO;


import com.groupe2.METOA.gestionProfilUtiisateur.entity.Role;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.Sexes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
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
public class UserReqDTO {

    @NotEmpty(message = "entrez votre nom !")
    private String nom;
    @NotEmpty(message = "entrez votre prenom !")
    private String prenom;
    private LocalDate dateNaissance;
    private String lieuNaissance;
    private Sexes sexe;

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


}
