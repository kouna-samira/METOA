package com.groupe2.METOA.gestionProfilUtiisateur.service.auth;

import com.groupe2.METOA.gestionProfilUtiisateur.dto.authentification.LoginReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.authentification.LoginResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.StatusUser;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import com.groupe2.METOA.gestionProfilUtiisateur.repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
             UserRepo userRepo,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResDTO login(LoginReqDTO dto) {

        User user = userRepo.findByEmailIgnoreCase(dto.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Email ou mot de passe incorrect")
                );

        if (!passwordEncoder.matches(dto.getPasse(), user.getPasse())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        if (user.getStatusUser() != StatusUser.ACTIF) {
            throw new RuntimeException("Votre compte n'est pas actif");
        }

        UserResDTO userResDTO = UserResDTO.builder()
                .idUser(user.getIdUser())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .sexe(user.getSexe().name())
                .telephone(user.getTelephone())
                .email(user.getEmail())
                .role(user.getRole())
                .statusUser(user.getStatusUser())
                .build();

        return LoginResDTO.builder()
                .token(null)
                .user(userResDTO)
                .build();
    }

    @Override
    public void register(UserReqDTO dto) {

        if (userRepo.findByEmailIgnoreCase(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Cette adresse email est déjà utilisée");
        }

        User user = User.builder()
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .dateNaissance(dto.getDateNaissance())
                .lieuNaissance(dto.getLieuNaissance())
                .sexe(dto.getSexe())
                .telephone(dto.getTelephone())
                .email(dto.getEmail())
                .passe(passwordEncoder.encode(dto.getPasse()))
                .role(dto.getRole())
                .statusUser(StatusUser.ACTIF)
                .visibiliteTelephone(true)
                .dateInscription(LocalDateTime.now())
                .build();

        userRepo.save(user);
    }
}