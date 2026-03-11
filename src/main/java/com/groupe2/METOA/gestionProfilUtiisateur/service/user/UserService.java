package com.groupe2.METOA.gestionProfilUtiisateur.service.user;


import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserResDTO createUser(UserReqDTO dto);

    UserResDTO getUserById(String id);

    List<UserResDTO> getAllUsers();

    UserResDTO updateUser(String id, UserReqDTO dto);

    void deleteUser(String id);

    UserResDTO getUserByEmail(String email);

    List<UserResDTO> getUserByNom(String nom);

    List<UserResDTO> getUserByPrenom(String prenom);

    Page<UserResDTO> globalSearch(String keyword, Pageable pageable);

    Page<UserResDTO> searchUsers(UserSearchDTO searchDTO, Pageable pageable);

}
