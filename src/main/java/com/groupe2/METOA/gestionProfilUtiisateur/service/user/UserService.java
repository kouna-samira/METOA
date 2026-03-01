package com.groupe2.METOA.gestionProfilUtiisateur.service.user;


import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserResDTO;

public interface UserService {
    void createUser(UserReqDTO userReqDTO);
    UserResDTO getFindByIdUser(String idUser );
    void UpdateUser(UserReqDTO userReqDTO, String idUser);
    void deleteUser(String idUser);
}
