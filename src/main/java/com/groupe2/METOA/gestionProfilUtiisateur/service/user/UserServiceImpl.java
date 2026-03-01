package com.groupe2.METOA.gestionProfilUtiisateur.service.user;


import com.groupe2.METOA.gestionProfilUtiisateur.classMapp.ProfilMapper;
import com.groupe2.METOA.gestionProfilUtiisateur.classMapp.UserMapper;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.Profil;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.StatusUser;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import com.groupe2.METOA.gestionProfilUtiisateur.exception.UserNoteFoundException;
import com.groupe2.METOA.gestionProfilUtiisateur.repository.ProfilRepo;
import com.groupe2.METOA.gestionProfilUtiisateur.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService{

    private final UserMapper userMapper;
    private final UserRepo userRepo;
    private final ProfilRepo profilRepo;
    private final ProfilMapper profilMapper;

    public UserServiceImpl(UserMapper userMapper, UserRepo userRepo, ProfilRepo profilRepo, ProfilMapper profilMapper) {
        this.userMapper = userMapper;
        this.userRepo = userRepo;
        this.profilRepo = profilRepo;
        this.profilMapper = profilMapper;
    }

    @Override
    public void createUser(UserReqDTO userReqDTO) {

        User user =  userMapper.toENTITY(userReqDTO);
        if (user.getSexe() == null) {
            user.setSexe("NON_DEFINI");
        }
        user.setDateInscription(LocalDateTime.now());
        user.setStatusUser(StatusUser.ACTIF);

        if (userReqDTO.getProfil() != null) {
            Profil profil = profilMapper.toEntity(userReqDTO.getProfil());

            profil.setUser(user);
            user.setProfil(profil);
        }


        this.userRepo.save(user);

    }

    @Override
    public UserResDTO getFindByIdUser(String idUser) {
        User user= this.userRepo.findById(idUser)
                .orElseThrow(()-> new UserNoteFoundException(idUser));
        return this.userMapper.toResDTO(user);
    }


    @Override
    public void UpdateUser(UserReqDTO userReqDTO, String idUser) {
        User user= this.userRepo.findById(idUser)
                .orElseThrow(()-> new UserNoteFoundException(idUser));

        user.setNom(userReqDTO.getNom());
        user.setPrenom(userReqDTO.getPrenom());
        user.setEmail(userReqDTO.getEmail());
        user.setTelephone(userReqDTO.getTelephone());
        user.setDateModification(LocalDateTime.now());


        if (userReqDTO.getProfil() != null) {
            if (user.getProfil() == null) user.setProfil(profilMapper.toEntity(userReqDTO.getProfil()));
            else profilMapper.updateProfilFromDTO(userReqDTO.getProfil(), user.getProfil());
        }

        this.userRepo.save(user);
    }

    @Override
    public void deleteUser(String idUser) {

        if (!userRepo.existsById(idUser)) {
            throw new UserNoteFoundException(idUser);
        }

        this.userRepo.deleteById(idUser);

    }
}
