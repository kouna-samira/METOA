package com.groupe2.METOA.gestionProfilUtiisateur.controller;

import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.ProfilReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.ProfilResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.service.profil.ProfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Profil", description = "Gestion de Profil")
@RestController
@RequestMapping(path = "api/v1/Profil")
public class ProfilController {

    private  final ProfilService profilService;

    public ProfilController(ProfilService profilService) {
        this.profilService = profilService;
    }

    @Operation(summary = "consuter un profil")
    @GetMapping(path = "/get_Profil_by_idUser/{idUser}")
    public ResponseEntity<ProfilResDTO> getProfi(@Valid @PathVariable String idUser){
        return ResponseEntity.status(200).body(this.profilService.getProfilByUserId(idUser));
    }


    @Operation(summary = "modifier un profil")
    @PatchMapping(path = "/update_Profil_by_idUser/{idUser}")
    public ResponseEntity<String> updateProfil(@PathVariable @Valid String idUser, @RequestBody @Valid ProfilReqDTO profilReqDTO){
        this.profilService.updateProfilByUserId(idUser,profilReqDTO);
        return ResponseEntity.status(202).body("Updated successfully!");
    }


    @Operation(summary = "supprimer un Profil")
    @DeleteMapping(path = "/delete_Profil_by_id/{idUser}")
    public ResponseEntity<String> deleteProfil(@PathVariable String idUser){
        this.profilService.deleteProfilByUserId(idUser);
        return ResponseEntity.status(202).body("Deleted successfully!");
    }
}
