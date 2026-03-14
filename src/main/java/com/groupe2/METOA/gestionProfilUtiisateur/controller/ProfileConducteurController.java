package com.groupe2.METOA.gestionProfilUtiisateur.controller;

import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileConducteur.ProfileConducteurReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.profileConducteur.ProfileConducteurResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.note_avis.entity.Badge;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.TyperDocument;
import com.groupe2.METOA.gestionProfilUtiisateur.service.profileConducteur.ProfileConducteurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Tag(name = "ProfileConducteur", description = "Gestion des conducteurs")
@RestController
@RequestMapping("api/v1/users/{userId}/profile-conducteur")
public class ProfileConducteurController {


    private final ProfileConducteurService profileConducteurService;

    public ProfileConducteurController(ProfileConducteurService service) {
        this.profileConducteurService = service;
    }



    @Operation(summary = "Créer un conducteur")
    @PostMapping("/{userId}")
    public ResponseEntity<String> createProfile(
            @PathVariable String userId,
            @Valid @RequestBody ProfileConducteurReqDTO dto){
        dto.setUserId(userId);
        this.profileConducteurService.createProfile(dto);
        return ResponseEntity.status(201).body("conductor create succefull");
    }

    @Operation(summary = " mettre à jour un conducteur")
    @PutMapping("/{userId}")
    public ResponseEntity<String> updateProfile(
            @PathVariable String userId,
            @Valid @RequestBody ProfileConducteurReqDTO dto){
        dto.setUserId(userId);
        this.profileConducteurService.updateProfile(dto);
        return ResponseEntity.status(202).body("update succefull");
    }


    @Operation(summary = "afficher un conducteur")
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileConducteurResDTO> getProfile(@PathVariable String userId) {
        return ResponseEntity.status(200).body(profileConducteurService.getProfileByUserId(userId));
    }

    @Operation(summary = "affcher tous les conducteurs")
    @GetMapping
    public ResponseEntity<Page<ProfileConducteurResDTO>> getAllProfiles(Pageable pageable){
        return ResponseEntity.status(200).body(profileConducteurService.getAllProfiles(pageable));
    }

    @Operation(summary = "affcher les conducteur par badge")
    @GetMapping("/badge/{badge}")
    public ResponseEntity<List<ProfileConducteurResDTO>> getDriversByBadge(@PathVariable Badge badge){
        return ResponseEntity.status(200).body(profileConducteurService.getDriversByBadge(badge));
    }

    @Operation(summary = "Supprimer le profil conducteur")
    @DeleteMapping
    public ResponseEntity<String> deleteProfile(@PathVariable String userId) {
        profileConducteurService.deleteProfile(userId);
        return ResponseEntity.status(202).body("conductor delete successfully");
    }



    @Operation(summary = "Uploader ou modifier la photo du conducteur")
    @PostMapping(value = "/photo",consumes = "multipart/form-data")
    public ResponseEntity<String> uploadPhoto(
            @PathVariable String userId,
            @RequestPart("file") MultipartFile file) {

        return ResponseEntity.ok(
                profileConducteurService.uploadPhotoByUserId(userId, file)
        );
    }



    @Operation(summary = "Uploader un document conducteur")
    @PostMapping(value  ="/document" ,consumes = "multipart/form-data")
    public ResponseEntity<Map<String, String>> uploadDocument(
            @PathVariable String userId,
            @RequestPart("file") MultipartFile file,
            @RequestPart("typerDocument") TyperDocument typerDocument) {

        String documentUrl =
                profileConducteurService.uploadDocument(userId, file, typerDocument);

        return ResponseEntity.ok(Map.of(
                "documentUrl", documentUrl
        ));
    }

    @Operation(summary = "Supprimer document")
    @DeleteMapping(value = "/document",consumes = "multipart/form-data")
    public ResponseEntity<String> deleteDocument(@PathVariable String userId) {
        profileConducteurService.deleteDocument(userId);
        return ResponseEntity.status(201).body("delete doc successfully");
    }

    @Operation(summary = "Afficher la photo")
    @GetMapping(value = "/photo",consumes = "multipart/form-data")
    public ResponseEntity<String> getPhoto(@PathVariable String userId) {
        return ResponseEntity.ok(
                profileConducteurService.getPhotoByUserId(userId)
        );
    }

    @Operation(summary = "Voir le document conducteur")
    @GetMapping(value = "/document",consumes = "multipart/form-data")
    public ResponseEntity<Map<String, String>> viewDocument(@PathVariable String userId) {

        String documentUrl = profileConducteurService.viewDocument(userId);

        return ResponseEntity.ok(Map.of(
                "documentUrl", documentUrl
        ));
    }
    @Operation(summary = "Supprimer la photo")
    @DeleteMapping(value = "/photo",consumes = "multipart/form-data")
    public ResponseEntity<String> deletePhoto(@PathVariable String userId) {
        profileConducteurService.deletePhotoByUserId(userId);
        return ResponseEntity.status(201).body("delete picture successfully");
    }

}
