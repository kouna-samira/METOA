package com.groupe2.METOA.gestionProfilUtiisateur.service.profileConducteur;

import com.groupe2.METOA.gestionProfilUtiisateur.classMapp.ProfileConducteurMapper;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileConducteur.ProfileConducteurReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileConducteur.ProfileConducteurResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.note_avis.entity.Badge;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.Profile;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.ProfileConducteur;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.TyperDocument;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import com.groupe2.METOA.gestionProfilUtiisateur.exception.NullableFillException;
import com.groupe2.METOA.gestionProfilUtiisateur.exception.ProfilNotFoundException;
import com.groupe2.METOA.gestionProfilUtiisateur.exception.ProfileAlreadyExistException;
import com.groupe2.METOA.gestionProfilUtiisateur.exception.UserNoteFoundException;
import com.groupe2.METOA.gestionProfilUtiisateur.repository.ProfileConducteurRepo;
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
import java.util.List;
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
    public ProfileConducteurResDTO createProfile( ProfileConducteurReqDTO dto) {

        if(profileConducteurRepo.existsByUserIdUser(dto.getUserId())){
            throw new ProfileAlreadyExistException("Profil conducteur déjà existant");
        }

        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new UserNoteFoundException("Utilisateur introuvable"));

        ProfileConducteur profile = profileConducteurMapper.toEntity(dto);

        profile.setUser(user);
        profile.setActif(true);
        profile.setNombreTrajetsEffectues(0);
        profile.setNoteMoyenne(0);
        profile.setBadge(Badge.NOUVEAU);
        profile.setDateCreationProfile(LocalDate.now());

        return profileConducteurMapper.toDto(profileConducteurRepo.save(profile));
    }

    @Override
    public ProfileConducteurResDTO updateProfile( ProfileConducteurReqDTO dto) {

        ProfileConducteur profile = profileConducteurRepo.findByUserIdUser(dto.getUserId())
                .orElseThrow(() -> new ProfilNotFoundException(" conducteur introuvable"));

        profileConducteurMapper.updateEntityFromDto(dto,profile);

        profile.setDateModificationProfile(LocalDate.now());
        profile.setBadge(calculerBadge(profile));

        return profileConducteurMapper.toDto(profileConducteurRepo.save(profile));
    }

    @Override
    public ProfileConducteurResDTO getProfileByUserId(String userId) {

        ProfileConducteur profile = profileConducteurRepo.findByUserIdUser(userId)
                .orElseThrow(() -> new ProfilNotFoundException("conducteur introuvable"));

        return profileConducteurMapper.toDto(profile);
    }

    @Override
    public Page<ProfileConducteurResDTO> getAllProfiles(Pageable pageable) {
        Page<ProfileConducteur> profiles = profileConducteurRepo.findAll(pageable);
        return profiles.map(profileConducteurMapper::toDto) ;
    }

    @Override
    public List<ProfileConducteurResDTO> getDriversByBadge(Badge badge) {

        return profileConducteurRepo.findByBadge(badge)
                .stream()
                .map(profileConducteurMapper::toDto)
                .toList();
    }

    @Override
    public void deleteProfile(String userId) {

        ProfileConducteur profile = profileConducteurRepo.findByUserIdUser(userId)
                .orElseThrow(() -> new ProfilNotFoundException("conducteur introuvable"));

        profileConducteurRepo.delete(profile);
    }

    private Badge calculerBadge(ProfileConducteur profile){

        double note = profile.getNoteMoyenne();
        int trajets = profile.getNombreTrajetsEffectues();

        if(note >= 4.8 && trajets >= 50)
            return Badge.SUPER_CONDUCTEUR;

        if(note >= 4 && trajets >= 10)
            return Badge.CONDUCTEUR_FIABLE;

        if(note < 3)
            return Badge.A_RISQUE;

        return Badge.NOUVEAU;
    }

    @Override
    public String uploadPhotoByUserId(String userId, MultipartFile file) {
        if (file == null || file.isEmpty()) throw new NullableFillException("Fichier vide ou nul");

        try {
            ProfileConducteur profile = profileConducteurRepo.findByUserIdUser(userId)
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
        ProfileConducteur profile = profileConducteurRepo.findByUserIdUser(userId)
                .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));
        return profile.getPhotoUrl();
    }

    @Override
    public void deletePhotoByUserId(String userId) {
        ProfileConducteur profile = profileConducteurRepo.findByUserIdUser(userId)
                .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));

        deleteLocalFile(profile.getPhotoUrl());
        profile.setPhotoUrl(null);
        profileConducteurRepo.save(profile);
    }

    @Override
    public String uploadDocument(String userId, MultipartFile file, TyperDocument typerDocument) {
        if (file == null || file.isEmpty()) throw new NullableFillException("Fichier vide ou nul");

        try {
            ProfileConducteur profile = profileConducteurRepo.findByUserIdUser(userId)
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
        ProfileConducteur profile = profileConducteurRepo.findByUserIdUser(userId)
                .orElseThrow(() -> new ProfilNotFoundException("Profil introuvable"));

        if (profile.getDocumentUrl() == null) throw new NullableFillException("Aucun document disponible");

        return profile.getDocumentUrl();
    }

    @Override
    public String viewDocument(String userId) {
        return downloadDocument(userId);
    }

    @Override
    public void deleteDocument(String userId) {
        ProfileConducteur profile = profileConducteurRepo.findByUserIdUser(userId)
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