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
@Tag(name = "conducteur", description = "Gestion des conducteurs")

@RestController
@RequestMapping("/api/v1/conducteurs")
public class ProfileConducteurController {

    private final ProfileConducteurService service;

    public ProfileConducteurController(ProfileConducteurService service) {
        this.service = service;
    }

    // CREATE
    @Operation(summary = "Créer un profil conducteur")
    @PostMapping("/users/{userId}")
    public ResponseEntity<String> createProfile(
            @PathVariable String userId,
            @Valid @RequestBody ProfileConducteurReqDTO dto){

        dto.setUserId(userId);
        service.createProfile(dto);

        return ResponseEntity.status(201)
                .body("Conducteur créé avec succès");
    }

    // UPDATE
    @Operation(summary = "Modifier un profil conducteur")
    @PutMapping("/users/{userId}")
    public ResponseEntity<String> updateProfile(
            @PathVariable String userId,
            @Valid @RequestBody ProfileConducteurReqDTO dto){

        dto.setUserId(userId);
        service.updateProfile(dto);

        return ResponseEntity.ok("Profil conducteur modifié");
    }

    // GET BY USER
    @Operation(summary = "Afficher un conducteur")
    @GetMapping("/users/{userId}")
    public ResponseEntity<ProfileConducteurResDTO> getProfile(
            @PathVariable String userId){

        return ResponseEntity.ok(
                service.getProfileByUserId(userId)
        );
    }

    // GET ALL
    @Operation(summary = "Afficher tous les conducteurs")
    @GetMapping
    public ResponseEntity<Page<ProfileConducteurResDTO>> getAllProfiles(
            Pageable pageable){

        return ResponseEntity.ok(
                service.getAllProfiles(pageable)
        );
    }

    // GET BY BADGE
    @Operation(summary = "Afficher les conducteurs par badge")
    @GetMapping("/badge/{badge}")
    public ResponseEntity<List<ProfileConducteurResDTO>> getDriversByBadge(
            @PathVariable Badge badge){

        return ResponseEntity.ok(
                service.getDriversByBadge(badge)
        );
    }

    // DELETE
    @Operation(summary = "Supprimer profil conducteur")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> deleteProfile(
            @PathVariable String userId){

        service.deleteProfile(userId);

        return ResponseEntity.ok("Profil conducteur supprimé");
    }

    // UPLOAD PHOTO
    @Operation(summary = "Uploader photo conducteur")
    @PostMapping(value = "/users/{userId}/photo", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadPhoto(
            @PathVariable String userId,
            @RequestPart("file") MultipartFile file){

        return ResponseEntity.ok(
                service.uploadPhotoByUserId(userId,file)
        );
    }

    // GET PHOTO
    @Operation(summary = "Afficher photo conducteur")
    @GetMapping("/users/{userId}/photo")
    public ResponseEntity<String> getPhoto(
            @PathVariable String userId){

        return ResponseEntity.ok(
                service.getPhotoByUserId(userId)
        );
    }

    // DELETE PHOTO
    @Operation(summary = "Supprimer photo conducteur")
    @DeleteMapping("/users/{userId}/photo")
    public ResponseEntity<String> deletePhoto(
            @PathVariable String userId){

        service.deletePhotoByUserId(userId);

        return ResponseEntity.ok("Photo supprimée");
    }

    // UPLOAD DOCUMENT
    @Operation(summary = "Uploader document conducteur")
    @PostMapping(value = "/users/{userId}/document", consumes = "multipart/form-data")
    public ResponseEntity<Map<String,String>> uploadDocument(
            @PathVariable String userId,
            @RequestPart("file") MultipartFile file,
            @RequestPart("type") TyperDocument typerDocument){

        String url = service.uploadDocument(userId,file,typerDocument);

        return ResponseEntity.ok(
                Map.of("documentUrl",url)
        );
    }

    // VIEW DOCUMENT
    @Operation(summary = "Voir document conducteur")
    @GetMapping("/users/{userId}/document")
    public ResponseEntity<Map<String,String>> viewDocument(
            @PathVariable String userId){

        String url = service.viewDocument(userId);

        return ResponseEntity.ok(
                Map.of("documentUrl",url)
        );
    }

    // DELETE DOCUMENT
    @Operation(summary = "Supprimer document conducteur")
    @DeleteMapping("/users/{userId}/document")
    public ResponseEntity<String> deleteDocument(
            @PathVariable String userId){

        service.deleteDocument(userId);

        return ResponseEntity.ok("Document supprimé");
    }

}
