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
@Tag(name = "Passager", description = "Gestion des passagers")
@RestController
@RequestMapping("/api/v1/users/{userId}/profile-passager")
public class ProfilePassagerController {

    private final ProfilePassagerService profilePassagerService;

    public ProfilePassagerController(ProfilePassagerService service) {
        this.profilePassagerService = service;
    }

    // CREATE
    @Operation(summary = "Créer un passager")
    @PostMapping
    public ResponseEntity<String> createPassager(
            @PathVariable String userId,
            @RequestBody ProfilePassagerReqDTO dto) {

        dto.setUserId(userId);
        profilePassagerService.createOrUpdateProfile(dto);

        return ResponseEntity.status(201)
                .body("Passager créé avec succès");
    }

    // UPDATE
    @Operation(summary = "Modifier un passager")
    @PutMapping
    public ResponseEntity<String> updateProfile(
            @PathVariable String userId,
            @RequestBody ProfilePassagerReqDTO dto) {

        dto.setUserId(userId);
        profilePassagerService.updateProfileByUserId(dto);

        return ResponseEntity.ok("Passager modifié avec succès");
    }

    // GET ONE
    @Operation(summary = "Afficher un passager")
    @GetMapping
    public ResponseEntity<ProfilePassagerResDTO> getProfile(
            @PathVariable String userId) {

        return ResponseEntity.ok(
                profilePassagerService.getProfileByUserId(userId)
        );
    }

    // GET ALL
    @Operation(summary = "Afficher tous les passagers")
    @GetMapping("/all")
    public ResponseEntity<Page<ProfilePassagerResDTO>> getAll(
            Pageable pageable) {

        return ResponseEntity.ok(
                profilePassagerService.getAllPassage(pageable)
        );
    }

    // DELETE
    @Operation(summary = "Supprimer un passager")
    @DeleteMapping
    public ResponseEntity<String> deleteProfile(
            @PathVariable String userId) {

        profilePassagerService.deleteProfileByUserId(userId);

        return ResponseEntity.ok("Passager supprimé");
    }

    // UPLOAD PHOTO
    @Operation(summary = "Ajouter ou modifier photo")
    @PostMapping(value = "/photo", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadPhoto(
            @PathVariable String userId,
            @RequestPart("file") MultipartFile file) {

        profilePassagerService.uploadPhotoByUserId(userId,file);

        return ResponseEntity.ok("Photo ajoutée avec succès");
    }

    // GET PHOTO
    @Operation(summary = "Afficher photo")
    @GetMapping("/photo")
    public ResponseEntity<?> getPhoto(@PathVariable String userId) {
        String url = profilePassagerService.getPhotoByUserId(userId);

        return ResponseEntity.status(302)
                .header("Location", url)
                .build();
    }

    // DELETE PHOTO
    @Operation(summary = "Supprimer photo")
    @DeleteMapping("/photo")
    public ResponseEntity<String> deletePhoto(
            @PathVariable String userId) {

        profilePassagerService.deletePhotoByUserId(userId);

        return ResponseEntity.ok("Photo supprimée");
    }

}