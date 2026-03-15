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

import java.util.List;


@Tag(name = "Profile Standard", description = "Gestion du profil utilisateur standard")
    @RestController
    @RequestMapping("api/v1/users/{userId}/profile")
    public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService service) {
        this.profileService = service;
    }



    @Operation(summary = "Créer  un profile standard")
    @PostMapping
    public ResponseEntity<String> createProfile(
            @PathVariable String userId,
            @RequestBody ProfileReqDTO dto) {
        dto.setUserId(userId);
        this.profileService.createProfile(dto);
        return ResponseEntity.status(201).body(" create profile successfully");
    }

    @PutMapping
    public ResponseEntity<String> updateProfile(
            @PathVariable String userId,
            @RequestBody ProfileReqDTO dto) {
        dto.setUserId(userId);
        this.profileService.updateProfileByUserId(dto);
        return ResponseEntity.status(200).body(" update profile successfully");
    }

    @Operation(summary = "Afficher un profile standard")
    @GetMapping
    public ResponseEntity<ProfileResDTO> getProfile(@PathVariable String userId) {
        return ResponseEntity.status(200).body(profileService.getProfileByUserId(userId));
    }

    @Operation(summary = "Afficher tous les profiles standard")
    @GetMapping
    public ResponseEntity<Page<ProfileResDTO>> getAllUsers(Pageable pageable) {

        return ResponseEntity.status(200).body(profileService.getAllProfiles(pageable));
    }


    @Operation(summary = "Supprimer le profil standard")
    @DeleteMapping
    public ResponseEntity<String> deleteProfile(@PathVariable String userId) {
        profileService.deleteProfileByUserId(userId);
        return ResponseEntity.status(202).body("delete profile successfully");
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
    public ResponseEntity<String> deletePhoto(@PathVariable String userId) {
        profileService.deletePhotoByUserId(userId);
        return ResponseEntity.status(201).body("delete picture successfully");
    }

    }


