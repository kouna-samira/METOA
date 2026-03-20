package com.groupe2.METOA.gestionProfilUtiisateur.controller;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileStandard.ProfileReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileStandard.ProfileResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.service.profile.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@Tag(name = "Profile Standard", description = "Gestion des utilisateurs standards")
@RestController
@RequestMapping("/api/v1/users/{userId}/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService service) {
        this.profileService = service;
    }

    // CREATE
    @Operation(summary = "Créer un profil standard")
    @PostMapping
    public ResponseEntity<String> createProfile(
            @PathVariable String userId,
            @RequestBody ProfileReqDTO dto) {

        dto.setUserId(userId);
        profileService.createProfile(dto);

        return ResponseEntity.status(201)
                .body("Profile créé avec succès");
    }

    // UPDATE
    @Operation(summary = "Modifier profil standard")
    @PutMapping
    public ResponseEntity<String> updateProfile(
            @PathVariable String userId,
            @RequestBody ProfileReqDTO dto) {

        dto.setUserId(userId);
        profileService.updateProfileByUserId(dto);

        return ResponseEntity.ok("Profile modifié avec succès");
    }

    // GET ONE
    @Operation(summary = "Afficher un profil standard")
    @GetMapping
    public ResponseEntity<ProfileResDTO> getProfile(
            @PathVariable String userId) {

        return ResponseEntity.ok(
                profileService.getProfileByUserId(userId)
        );
    }

    // GET ALL PROFILES
    @Operation(summary = "Afficher tous les profils standard")
    @GetMapping("/all")
    public ResponseEntity<Page<ProfileResDTO>> getAllProfiles(
            Pageable pageable) {

        return ResponseEntity.ok(
                profileService.getAllProfiles(pageable)
        );
    }

    // DELETE
    @Operation(summary = "Supprimer profil standard")
    @DeleteMapping
    public ResponseEntity<String> deleteProfile(
            @PathVariable String userId) {

        profileService.deleteProfileByUserId(userId);

        return ResponseEntity.ok("Profile supprimé");
    }

    // UPLOAD PHOTO
    @Operation(summary = "Uploader ou modifier photo")
    @PostMapping(value = "/photo", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadPhoto(
            @PathVariable String userId,
            @RequestPart("file") MultipartFile file) {

        return ResponseEntity.ok(
                profileService.uploadPhotoByUserId(userId,file)
        );
    }

    // GET PHOTO
    @Operation(summary = "Afficher la photo")
    @GetMapping("/photo")
    public ResponseEntity<?> getPhoto(@PathVariable String userId) {
        String url = profileService.getPhotoByUserId(userId);

        return ResponseEntity.status(302)
                .header("Location", url)
                .build();
    }

    // DELETE PHOTO
    @Operation(summary = "Supprimer la photo")
    @DeleteMapping("/photo")
    public ResponseEntity<String> deletePhoto(
            @PathVariable String userId) {

        profileService.deletePhotoByUserId(userId);

        return ResponseEntity.ok("Photo supprimée");
    }

}