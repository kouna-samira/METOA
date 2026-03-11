package com.groupe2.METOA.gestionProfilUtiisateur.controller;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileStandard.ProfileReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileStandard.ProfileResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.service.profile.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Tag(name = "Profile Standard", description = "Gestion du profil utilisateur standard")
    @RestController
    @RequestMapping("api/v1/users/{userId}/profile")
    public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService service) {
        this.profileService = service;
    }



    @Operation(summary = "Créer ou mettre à jour un profil standard")
    @PostMapping
    public ResponseEntity<ProfileResDTO> createOrUpdateProfile(
            @PathVariable String userId,
            @RequestBody ProfileReqDTO dto
    ) throws Exception {
        dto.setUserId(userId);
        return ResponseEntity.ok(
                profileService.createProfile(dto)
        );
    }



    @Operation(summary = "Afficher le profil standard")
    @GetMapping
    public ResponseEntity<ProfileResDTO> getProfile(@PathVariable String userId) {
        return ResponseEntity.ok(profileService.getProfileByUserId(userId));
    }

    @Operation(summary = "Afficher tous les profiles standard")
    @GetMapping
    public ResponseEntity<List<ProfileResDTO>> getAllUsers() {

        return ResponseEntity.ok(profileService.getAllProfiles());
    }


    @Operation(summary = "Supprimer le profil standard")
    @DeleteMapping
    public ResponseEntity<Void> deleteProfile(@PathVariable String userId) {
        profileService.deleteProfileByUserId(userId);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Uploader ou modifier la photo")
    @PostMapping(value = "/photo",consumes = "multipart/form-data")
    public ResponseEntity<String> uploadPhoto(
            @PathVariable String userId,
            @RequestPart("file") MultipartFile file) {

        return ResponseEntity.ok(
                profileService.uploadPhotoByUserId(userId, file)
        );
    }


    @Operation(summary = "Afficher la photo")
    @GetMapping(value = "/photo",consumes = "multipart/form-data")
    public ResponseEntity<String> getPhoto(@PathVariable String userId) {
        return ResponseEntity.ok(
                profileService.getPhotoByUserId(userId)
        );
    }


    @Operation(summary = "Supprimer la photo")
    @DeleteMapping(value = "/photo",consumes = "multipart/form-data")
    public ResponseEntity<Void> deletePhoto(@PathVariable String userId) {
        profileService.deletePhotoByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    }


