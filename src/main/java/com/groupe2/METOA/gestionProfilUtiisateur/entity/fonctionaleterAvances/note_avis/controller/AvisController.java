package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.controller;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.dto.AvisReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.dto.AvisResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.service.AvisService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avis")
@RequiredArgsConstructor
@Tag(name = "avis", description = "notation et avis")
public class AvisController {

    private final AvisService avisService;

    // Créer un avis
    @PostMapping
    public AvisResDTO createAvis(@Valid @RequestBody AvisReqDTO dto){
        return avisService.createAvis(dto);
    }

    // Liste publique d'avis (non paginée)
    @GetMapping("/user/{userId}/public")
    public List<AvisResDTO> getAvisByUser(@PathVariable String userId){
        return avisService.getAvisPublicByUser(userId);
    }

    // Liste paginée d'avis
    @GetMapping("/user/{userId}")
    public Page<AvisResDTO> getAvisUser(
            @PathVariable String userId,
            Pageable pageable){
        return avisService.getAvisUser(userId,pageable);
    }

    // Supprimer un avis
    @DeleteMapping("/{avisId}")
    public void deleteAvis(@PathVariable String avisId){
        avisService.deleteAvis(avisId);
    }
}