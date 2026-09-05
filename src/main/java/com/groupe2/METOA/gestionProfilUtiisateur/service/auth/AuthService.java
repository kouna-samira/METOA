package com.groupe2.METOA.gestionProfilUtiisateur.service.auth;

import com.groupe2.METOA.gestionProfilUtiisateur.dto.authentification.LoginReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.authentification.LoginResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserReqDTO;

public interface AuthService {

    LoginResDTO login(LoginReqDTO dto);

    void register(UserReqDTO dto);
}
