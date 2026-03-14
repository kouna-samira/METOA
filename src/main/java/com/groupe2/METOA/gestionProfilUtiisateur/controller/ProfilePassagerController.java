package com.groupe2.METOA.gestionProfilUtiisateur.controller;

import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profilePassager.ProfilePassagerReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profilePassager.ProfilePassagerResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.service.profiePassager.ProfilePassagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Tag(name = "ProfilePassager", description = "Gestion des passagers")
    @RestController
    @RequestMapping("api/v1/users/{userId}/profile-passager")
    public class ProfilePassagerController {

        private final ProfilePassagerService profilePassagerService;

        public ProfilePassagerController(ProfilePassagerService service) {
            this.profilePassagerService = service;
        }

    @Operation(summary = "Créer un passager ")
    @PostMapping
    public ResponseEntity<String> createPassager(
            @PathVariable String userId,
            @RequestBody ProfilePassagerReqDTO dto) {

        dto.setUserId(userId);
this. profilePassagerService.createOrUpdateProfile( dto);
        return ResponseEntity.status(201).body("create passage successfully");
    }

    @Operation(summary = "Modifier un passager ")
    @PutMapping
    public ResponseEntity<String> updateProfile(
            @PathVariable String userId,
            @RequestBody ProfilePassagerReqDTO dto) {
            dto.setUserId(userId);
        profilePassagerService.updateProfileByUserId( dto);
        return ResponseEntity.status(202).body("update passage successfully");
    }

    @Operation(summary = "Afficher un passager ")
    @GetMapping(consumes = "application/json")
    public ResponseEntity<ProfilePassagerResDTO> getProfile(@PathVariable String userId) {
        return ResponseEntity.status(200).body(profilePassagerService.getProfileByUserId(userId));
    }

    @Operation(summary = "Afficher tous passager ")
    @GetMapping(consumes = "application/json")
    public ResponseEntity<Page<ProfilePassagerResDTO>> getAll(Pageable pageable) {
        return ResponseEntity.status(200).body(profilePassagerService.getAllPassage(pageable));
    }


    @Operation(summary = "Supprimer un passager ")
    @DeleteMapping
    public ResponseEntity<String> deleteProfile(@PathVariable String userId) {
        profilePassagerService.deleteProfileByUserId(userId);
        return ResponseEntity.status(202).body("passage delete successfully");
    }

    @Operation(summary = "Ajouter ou modifier la photo du profile")
    @PostMapping(value = "/photo",consumes = "multipart/form-data")
    public ResponseEntity<String> uploadPhoto(
            @PathVariable String userId,
            @RequestPart("file") MultipartFile file) {
        profilePassagerService.uploadPhotoByUserId(userId, file);
        return ResponseEntity.status(201).body("add picture successfully");
    }

    @Operation(summary = "Afficher la photo du profile")
    @GetMapping(value = "/photo",consumes = "multipart/form-data")
    public ResponseEntity<String> getPhoto(@PathVariable String userId) {
        return ResponseEntity.status(200).body(profilePassagerService.getPhotoByUserId(userId));
    }

    @Operation(summary = "Supprimer la photo du profile")
    @DeleteMapping(value = "/photo",consumes = "multipart/form-data")
    public ResponseEntity<String> deletePhoto(@PathVariable String userId) {
        profilePassagerService.deletePhotoByUserId(userId);
        return ResponseEntity.status(202).body("delete picture successfully");
    }

    }
