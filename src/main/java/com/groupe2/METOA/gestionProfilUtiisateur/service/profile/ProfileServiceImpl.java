package com.groupe2.METOA.gestionProfilUtiisateur.service.profile;


import com.groupe2.METOA.gestionProfilUtiisateur.classMapp.ProfileMapper;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileStandard.ProfileReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileStandard.ProfileResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.Profile;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import com.groupe2.METOA.gestionProfilUtiisateur.exception.ProfilNotFoundException;
import com.groupe2.METOA.gestionProfilUtiisateur.exception.UserAlreadyExisteException;
import com.groupe2.METOA.gestionProfilUtiisateur.exception.UserNoteFoundException;
import com.groupe2.METOA.gestionProfilUtiisateur.repository.ProfileRepo;
import com.groupe2.METOA.gestionProfilUtiisateur.repository.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepo profileRepo;
    private final ProfileMapper profileMapper;
    private final UserRepo userRepo;
    private final Path uploadDir = Paths.get("uploads/profils/");


    public ProfileServiceImpl(ProfileRepo profileRepo, ProfileMapper profileMapper, UserRepo userRepo) {
        this.profileRepo = profileRepo;
        this.profileMapper = profileMapper;
        this.userRepo = userRepo;
    }

    @Override
    public ProfileResDTO createProfile(ProfileReqDTO dto) {

        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new UserNoteFoundException(dto.getUserId()));

        if (profileRepo.findByUser_IdUser(dto.getUserId()).isPresent()) {
            throw new UserAlreadyExisteException("Cet utilisateur possède déjà un profil standard");
        }

        Profile profile = profileMapper.toEntity(dto);
        profile.setUser(user);
        profile.setDateCreationProfile(LocalDate.now());
        Profile savedProfile = profileRepo.save(profile);


        return profileMapper.toDto(savedProfile);
    }

    @Override
    public ProfileResDTO getProfileByUserId(String userId) {
        Profile profile = profileRepo.findByUser_IdUser(userId)
                .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));
        return profileMapper.toDto(profile);
    }
    @Override
    public List<ProfileResDTO> getAllProfiles() {

        return profileRepo.findAll()
                .stream()
                .map(profileMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public ProfileResDTO updateProfileByUserId(String userId, ProfileReqDTO dto) {
        Profile profile = profileRepo.findByUser_IdUser(userId)
                .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));

        profileMapper.updateEntityFromDto(dto, profile);
        profile.setDateModificationProfile(LocalDate.now());

        Profile savedProfile = profileRepo.save(profile);



        return profileMapper.toDto(savedProfile);
    }

    @Override
    public void deleteProfileByUserId(String userId) {
        Profile profile = profileRepo.findByUser_IdUser(userId)
                .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));

        deletePhotoByUserId(userId);
        profileRepo.delete(profile);
    }

    @Override
    public String uploadPhotoByUserId(String userId, MultipartFile file) {
        try {
            Profile profile = profileRepo.findByUser_IdUser(userId)
                    .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));

            Path folderPath = uploadDir;
            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = folderPath.resolve(filename);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Supprime l’ancienne photo si elle existe
            deleteLocalFile(profile.getPhotoUrl());

            profile.setPhotoUrl("/files/profils/" + filename);
            profileRepo.save(profile);

            return profile.getPhotoUrl();
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l’upload de la photo : " + e.getMessage(), e);
        }
    }

    @Override
    public String getPhotoByUserId(String userId) {
        Profile profile = profileRepo.findByUser_IdUser(userId)
                .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));
        return profile.getPhotoUrl();
    }


    @Override
    public void deletePhotoByUserId(String userId) {
        Profile profile = profileRepo.findByUser_IdUser(userId)
                .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));

        deleteLocalFile(profile.getPhotoUrl());
        profile.setPhotoUrl(null);
        profileRepo.save(profile);
    }

    private void deleteLocalFile(String fileUrl) {
        if (fileUrl == null) return;
        try {
            Path filePath = uploadDir.resolve(fileUrl.replace("/files/profils/", ""));
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
        } catch (IOException e) {
            System.err.println("Impossible de supprimer le fichier : " + fileUrl);
        }
    }


}