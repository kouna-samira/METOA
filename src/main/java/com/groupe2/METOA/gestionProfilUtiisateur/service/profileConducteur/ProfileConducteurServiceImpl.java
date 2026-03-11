package com.groupe2.METOA.gestionProfilUtiisateur.service.profileConducteur;

import com.groupe2.METOA.gestionProfileUtilisateur.classMapp.ProfileConducteurMapper;
import com.groupe2.METOA.gestionProfileUtilisateur.dto.profilDTO.profileConducteur.ProfileConducteurReqDTO;
import com.groupe2.METOA.gestionProfileUtilisateur.dto.profilDTO.profileConducteur.ProfileConducteurResDTO;
import com.groupe2.METOA.gestionProfileUtilisateur.entity.profil.ProfileConducteur;
import com.groupe2.METOA.gestionProfileUtilisateur.entity.profil.TyperDocument;
import com.groupe2.METOA.gestionProfileUtilisateur.entity.user.User;
import com.groupe2.METOA.gestionProfileUtilisateur.exception.ProfilNotFoundException;
import com.groupe2.METOA.gestionProfileUtilisateur.exception.UserNoteFoundException;
import com.groupe2.METOA.gestionProfileUtilisateur.repository.ProfileConducteurRepo;
import com.groupe2.METOA.gestionProfileUtilisateur.repository.UserRepo;
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
public class ProfileConducteurServiceImpl implements ProfileConducteurService {


    private final ProfileConducteurRepo profileConducteurRepo;
    private final ProfileConducteurMapper profileConducteurMapper;
    private final UserRepo userRepo;
    private final Path uploadDir = Paths.get("uploads/conducteurs/");

    public ProfileConducteurServiceImpl(ProfileConducteurRepo profileConducteurRepo, ProfileConducteurMapper profileConducteurMapper, UserRepo userRepo) {
        this.profileConducteurRepo = profileConducteurRepo;
        this.profileConducteurMapper = profileConducteurMapper;
        this.userRepo = userRepo;
    }


    @Override
    public ProfileConducteurResDTO createOrUpdateProfile(ProfileConducteurReqDTO dto) {

        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new UserNoteFoundException(dto.getUserId()));

        Optional<ProfileConducteur> optionalProfile =
                profileConducteurRepo.findByUser_IdUser(dto.getUserId());

        ProfileConducteur profile;
        if (optionalProfile.isPresent()) {
            profile = optionalProfile.get();
            profile.setAdresse(dto.getAdresse());
            profile.setBio(dto.getBio());
            profile.setVehicule(dto.getVehicule());
            profile.setPreferences(dto.getPreferences());
            profile.setDateModificationProfile(LocalDate.now());
        } else {
            profile = new ProfileConducteur();
            profile.setUser(user);
            profile.setAdresse(dto.getAdresse());
            profile.setBio(dto.getBio());
            profile.setVehicule(dto.getVehicule());
            profile.setPreferences(dto.getPreferences());
            profile.setDateCreationProfile(LocalDate.now());
            profile.setDateModificationProfile(LocalDate.now());
        }


        profileConducteurRepo.save(profile);
        return profileConducteurMapper.toDto(profile);
    }

    @Override
    public ProfileConducteurResDTO getProfileByUserId(String userId) {
        ProfileConducteur profile = profileConducteurRepo.findByUser_IdUser(userId)
                .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));
        return profileConducteurMapper.toDto(profile);
    }

    @Override
    public ProfileConducteurResDTO updateProfileByUserId(String userId,
                                                         ProfileConducteurReqDTO dto,
                                                         MultipartFile photo) {
        ProfileConducteur profile = profileConducteurRepo.findByUser_IdUser(userId)
                .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));

        profileConducteurMapper.updateEntityFromDto(dto, profile);
        profile.setDateModificationProfile(LocalDate.now());

        ProfileConducteur savedProfile = profileConducteurRepo.save(profile);

        if (photo != null && !photo.isEmpty()) {
            uploadPhotoByUserId(userId, photo);
        }

        return profileConducteurMapper.toDto(savedProfile);
    }

    @Override
    public void deleteProfileByUserId(String userId) {
        ProfileConducteur profile = profileConducteurRepo.findByUser_IdUser(userId)
                .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));

        deletePhotoByUserId(userId);


        profileConducteurRepo.delete(profile);
    }

    @Override
    public String uploadPhotoByUserId(String userId, MultipartFile file) {
        if (file == null || file.isEmpty()) throw new RuntimeException("Fichier vide ou nul");

        try {
            ProfileConducteur profile = profileConducteurRepo.findByUser_IdUser(userId)
                    .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));

            if (!Files.exists(uploadDir)) Files.createDirectories(uploadDir);

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadDir.resolve(filename);

            // Supprime ancienne photo
            deleteLocalFile(profile.getPhotoUrl());

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            String photoUrl = "/files/conducteurs/" + filename;

            profile.setPhotoUrl(photoUrl);
            profileConducteurRepo.save(profile);

            return photoUrl;
        } catch (IOException e) {
            throw new RuntimeException("Erreur upload photo : " + e.getMessage(), e);
        }
    }

    @Override
    public String getPhotoByUserId(String userId) {
        ProfileConducteur profile = profileConducteurRepo.findByUser_IdUser(userId)
                .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));
        return profile.getPhotoUrl();
    }

    @Override
    public void deletePhotoByUserId(String userId) {
        ProfileConducteur profile = profileConducteurRepo.findByUser_IdUser(userId)
                .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));

        deleteLocalFile(profile.getPhotoUrl());
        profile.setPhotoUrl(null);
        profileConducteurRepo.save(profile);
    }

    @Override
    public String uploadDocument(String userId, MultipartFile file, TyperDocument typerDocument) {
        if (file == null || file.isEmpty()) throw new RuntimeException("Fichier vide ou nul");

        try {
            ProfileConducteur profile = profileConducteurRepo.findByUser_IdUser(userId)
                    .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));

            if (!Files.exists(uploadDir)) Files.createDirectories(uploadDir);

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadDir.resolve(filename);

            // Supprime ancien document
            deleteLocalFile(profile.getDocumentUrl());

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            String documentUrl = "/files/conducteurs/" + filename;

            profile.setDocumentUrl(documentUrl);
            profile.setDocumentName(file.getOriginalFilename());
            profile.setDocumentType(typerDocument);
            profileConducteurRepo.save(profile);

            return documentUrl;
        } catch (IOException e) {
            throw new RuntimeException("Erreur upload document : " + e.getMessage(), e);
        }
    }

    @Override
    public String downloadDocument(String userId) {
        ProfileConducteur profile = profileConducteurRepo.findByUser_IdUser(userId)
                .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));

        if (profile.getDocumentUrl() == null) throw new ProfilNotFoundException("Aucun document disponible");

        return profile.getDocumentUrl();
    }

    @Override
    public String viewDocument(String userId) {
        return downloadDocument(userId);
    }

    @Override
    public void deleteDocument(String userId) {
        ProfileConducteur profile = profileConducteurRepo.findByUser_IdUser(userId)
                .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));

        deleteLocalFile(profile.getDocumentUrl());
        profile.setDocumentUrl(null);
        profile.setDocumentName(null);
        profile.setDocumentType(null);
        profileConducteurRepo.save(profile);
    }

    // --- Méthode utilitaire ---
    private void deleteLocalFile(String fileUrl) {
        if (fileUrl == null) return;
        try {
            Path filePath = uploadDir.resolve(fileUrl.replace("/files/conducteurs/", ""));
            if (Files.exists(filePath)) Files.delete(filePath);
        } catch (IOException e) {
            System.err.println("Impossible de supprimer le fichier : " + fileUrl);
        }
    }

}