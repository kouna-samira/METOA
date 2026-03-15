package com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.Role;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.StatusUser;
import lombok.Data;

@Data
public class UserSearchDTO {

    private String nom;

    private String prenom;

    private String email;

    private String ville;

    private Role role;

    private StatusUser statusUser;

}