package com.groupe2.METOA.gestionProfilUtiisateur.service.user;


import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserResDTO;

import java.util.List;

public interface UserService {

    UserResDTO createUser(UserReqDTO dto);

    UserResDTO getUserById(String id);

    List<UserResDTO> getAllUsers();

    UserResDTO updateUser(String id, UserReqDTO dto);

    void deleteUser(String id);

    // nouvelles méthodes

    UserResDTO getUserByEmail(String email);

    List<UserResDTO> getUserByNom(String nom);

    List<UserResDTO> getUserByPrenom(String prenom);

}
