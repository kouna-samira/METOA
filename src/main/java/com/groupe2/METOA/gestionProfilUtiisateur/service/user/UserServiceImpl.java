package com.groupe2.METOA.gestionProfilUtiisateur.service.user;


import com.groupe2.METOA.gestionProfilUtiisateur.classMapp.UserMapper;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.userDTO.UserSearchDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.StatusUser;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import com.groupe2.METOA.gestionProfilUtiisateur.exception.UserAlreadyExisteException;
import com.groupe2.METOA.gestionProfilUtiisateur.exception.UserNoteFoundException;
import com.groupe2.METOA.gestionProfilUtiisateur.repository.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResDTO createUser(UserReqDTO dto) {

        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExisteException("Email déjà utilisé");
        }


        User user = userMapper.toEntity(dto);

        user.setPasse(passwordEncoder.encode(dto.getPasse()));
        user.setDateInscription(LocalDateTime.now());
        user.setStatusUser(StatusUser.ACTIF);

        User savedUser = userRepo.save(user);

        return userMapper.toDto(savedUser);
    }

    @Override
    public UserResDTO getUserById(String id) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNoteFoundException(id));

        return userMapper.toDto(user);
    }

    @Override
    public Page<UserResDTO> getAllUsers(Pageable pageable) {

        Page<User> users = userRepo.findAll(pageable);

        return users.map(userMapper::toDto);
    }

    @Override
    public UserResDTO updateUser(String id, UserReqDTO dto) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNoteFoundException(id));

        userMapper.updateEntityFromDto(dto, user);

        if (dto.getPasse() != null && !dto.getPasse().isBlank()) {
            user.setPasse(passwordEncoder.encode(dto.getPasse()));
        }

        user.setDateModification(LocalDateTime.now());

        User updatedUser = userRepo.save(user);

        return userMapper.toDto(updatedUser);
    }

    @Override
    public void deleteUser(String id) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNoteFoundException(id));

        userRepo.delete(user);
    }

    @Override
    public UserResDTO getUserByEmail(String email) {

        User user = userRepo.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserNoteFoundException("Utilisateur introuvable"));

        return userMapper.toDto(user);
    }

    @Override
    public List<UserResDTO> getUserByNom(String nom) {

        return userRepo.findByNomIgnoreCase(nom)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public List<UserResDTO> getUserByPrenom(String prenom) {

        return userRepo.findByPrenomIgnoreCase(prenom)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public Page<UserResDTO> globalSearch(String keyword, Pageable pageable) {

        return userRepo.searchUsers(keyword, pageable)
                .map(userMapper::toDto);
    }

    @Override
    public Page<UserResDTO> searchUsers(UserSearchDTO searchDTO, Pageable pageable) {

        Specification<User> spec = Specification.where(null);

        if (searchDTO.getNom() != null && !searchDTO.getNom().isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("nom")),
                            "%" + searchDTO.getNom().toLowerCase() + "%"));
        }

        if (searchDTO.getPrenom() != null && !searchDTO.getPrenom().isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("prenom")),
                            "%" + searchDTO.getPrenom().toLowerCase() + "%"));
        }

        if (searchDTO.getEmail() != null && !searchDTO.getEmail().isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("email"), searchDTO.getEmail()));
        }

        if (searchDTO.getVille() != null && !searchDTO.getVille().isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("ville")),
                            "%" + searchDTO.getVille().toLowerCase() + "%"));
        }

        if (searchDTO.getRole() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("role"), searchDTO.getRole()));
        }

        if (searchDTO.getStatusUser() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("statusUser"), searchDTO.getStatusUser()));
        }

        Page<User> pageUsers = userRepo.findAll(spec, pageable);

        return pageUsers.map(userMapper::toDto);
    }
}