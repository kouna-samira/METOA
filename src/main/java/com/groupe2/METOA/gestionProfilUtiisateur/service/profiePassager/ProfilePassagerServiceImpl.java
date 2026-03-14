package com.groupe2.METOA.gestionProfilUtiisateur.service.profiePassager;

import com.groupe2.METOA.gestionProfilUtiisateur.classMapp.ProfilePassagerMapper;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profilePassager.ProfilePassagerReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profilePassager.ProfilePassagerResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileStandard.ProfileResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.Profile;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.ProfilePassager;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import com.groupe2.METOA.gestionProfilUtiisateur.exception.ProfilNotFoundException;
import com.groupe2.METOA.gestionProfilUtiisateur.exception.UserNoteFoundException;
import com.groupe2.METOA.gestionProfilUtiisateur.repository.ProfilePassagerRepo;
import com.groupe2.METOA.gestionProfilUtiisateur.repository.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class ProfilePassagerServiceImpl implements ProfilePassagerService {

    private final ProfilePassagerRepo profilePassagerRepo;
    private final ProfilePassagerMapper profilePassagerMapper;
    private final UserRepo userRepo;
    private final Path uploadDir = Paths.get("uploads/passagers/");

    public ProfilePassagerServiceImpl(ProfilePassagerRepo profilePassagerRepo, ProfilePassagerMapper profilePassagerMapper, UserRepo userRepo) {
        this.profilePassagerRepo = profilePassagerRepo;
        this.profilePassagerMapper = profilePassagerMapper;
        this.userRepo = userRepo;
    }


    @Override
    public ProfilePassagerResDTO createOrUpdateProfile(ProfilePassagerReqDTO dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new UserNoteFoundException(dto.getUserId()));

        Optional<ProfilePassager> optionalProfile =
                profilePassagerRepo.findByUser_IdUser(dto.getUserId());

        ProfilePassager profile;
        if (optionalProfile.isPresent()) {
            profile = optionalProfile.get();
            profile.setAdresse(dto.getAdresse());
            profile.setPreferences(dto.getPreferences());
            profile.setBio(dto.getBio());
            profile.setDateModificationProfile(LocalDate.now());
        } else {
            profile = new ProfilePassager();
            profile.setUser(user);
            profile.setAdresse(dto.getAdresse());
            profile.setPreferences(dto.getPreferences());
            profile.setBio(dto.getBio());
            profile.setDateCreationProfile(LocalDate.now());
            profile.setDateModificationProfile(LocalDate.now());
        }



        profilePassagerRepo.save(profile);
        return profilePassagerMapper.toDto(profile);
    }

    @Override
    public ProfilePassagerResDTO getProfileByUserId(String userId) {
        ProfilePassager profile = profilePassagerRepo.findByUser_IdUser(userId)
                .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));
        return profilePassagerMapper.toDto(profile);
    }

    @Override
    public Page<ProfilePassagerResDTO> getAllPassage(Pageable pageable) {

        Page<ProfilePassager> profiles = profilePassagerRepo.findAll(pageable);

        return profiles.map(profilePassagerMapper::toDto);
    }

    @Override
    public ProfilePassagerResDTO updateProfileByUserId( ProfilePassagerReqDTO dto) {
        ProfilePassager profile = profilePassagerRepo.findByUser_IdUser(dto.getUserId())
                .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));

        profilePassagerMapper.updateEntityFromDto(dto, profile);
        profile.setDateModificationProfile(LocalDate.now());

        ProfilePassager savedProfile = profilePassagerRepo.save(profile);


        return profilePassagerMapper.toDto(savedProfile);
    }

    @Override
    public void deleteProfileByUserId(String userId) {
        ProfilePassager profile = profilePassagerRepo.findByUser_IdUser(userId)
                .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));

        deletePhotoByUserId(userId);
        profilePassagerRepo.delete(profile);
    }

    @Override
    public String uploadPhotoByUserId(String userId, MultipartFile file) {
        if (file == null || file.isEmpty()) throw new RuntimeException("Fichier vide ou nul");

        try {
            ProfilePassager profile = profilePassagerRepo.findByUser_IdUser(userId)
                    .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));

            if (!Files.exists(uploadDir)) Files.createDirectories(uploadDir);

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadDir.resolve(filename);

            // Supprime ancienne photo
            deleteLocalFile(profile.getPhotoUrl());

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            String photoUrl = "/files/passagers/" + filename;

            profile.setPhotoUrl(photoUrl);
            profilePassagerRepo.save(profile);

            return photoUrl;
        } catch (IOException e) {
            throw new RuntimeException("Erreur upload photo : " + e.getMessage(), e);
        }
    }

    @Override
    public String getPhotoByUserId(String userId) {
        ProfilePassager profile = profilePassagerRepo.findByUser_IdUser(userId)
                .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));
        return profile.getPhotoUrl();
    }

    @Override
    public String updatePhotoByUserId(String userId, MultipartFile file) {
        deletePhotoByUserId(userId);
        return uploadPhotoByUserId(userId, file);
    }

    @Override
    public void deletePhotoByUserId(String userId) {
        ProfilePassager profile = profilePassagerRepo.findByUser_IdUser(userId)
                .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));

        deleteLocalFile(profile.getPhotoUrl());
        profile.setPhotoUrl(null);
        profilePassagerRepo.save(profile);
    }

    // --- Méthode utilitaire ---
    private void deleteLocalFile(String fileUrl) {
        if (fileUrl == null) return;
        try {
            Path filePath = uploadDir.resolve(fileUrl.replace("/files/passagers/", ""));
            if (Files.exists(filePath)) Files.delete(filePath);
        } catch (IOException e) {
            System.err.println("Impossible de supprimer le fichier : " + fileUrl);
        }
    }
}