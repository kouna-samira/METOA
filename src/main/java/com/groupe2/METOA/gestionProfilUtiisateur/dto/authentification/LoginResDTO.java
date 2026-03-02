package com.groupe2.METOA.gestionProfilUtiisateur.dto.authentification;


import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserResDTO;
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
public class LoginResDTO {
    private String token;
    private UserResDTO userResDTO;
}
