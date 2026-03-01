package com.groupe2.METOA.gestionProfilUtiisateur.dto.role;


import com.groupe2.METOA.gestionProfilUtiisateur.entity.Role;
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
public class RoleResDTO {
    @Schema(description = "Rôle de l'utilisateur")
    private Role role;

}
