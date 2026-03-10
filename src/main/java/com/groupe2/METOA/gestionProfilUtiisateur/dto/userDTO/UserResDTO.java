package com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO;


import com.groupe2.METOA.gestionProfilUtiisateur.entity.Role;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.StatusUser;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class UserResDTO {
    private String idUser;
    private String nom;
    private String prenom;
    private String sexe;
    private String telephone;
    private String email;
    @Schema(description = "Rôle de l'utilisateur")
    private Role role;
    private StatusUser statusUser;
    private ProfilReqDTO profil;
}
