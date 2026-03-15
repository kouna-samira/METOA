package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.note_avis.controller;

import com.groupe2.METOA.gestionProfileUtilisateur.entity.note_avis.dto.AvisReqDTO;
import com.groupe2.METOA.gestionProfileUtilisateur.entity.note_avis.dto.AvisResDTO;
import com.groupe2.METOA.gestionProfileUtilisateur.entity.note_avis.dto.StatistiqueAvisDTO;
import com.groupe2.METOA.gestionProfileUtilisateur.entity.note_avis.service.AvisService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/avis")
public class AvisController {

    private final AvisService avisService;

    public AvisController(AvisService avisService) {
        this.avisService = avisService;
    }

    @PostMapping("/{auteurId}")
    public ResponseEntity<AvisResDTO> noter(
            @PathVariable String auteurId,
            @RequestBody AvisReqDTO dto) {

        return ResponseEntity.ok(
                avisService.donnerAvis(auteurId, dto)
        );
    }

    @PutMapping("/{avisId}")
    public ResponseEntity<AvisResDTO> modifier(
            @PathVariable String avisId,
            @RequestParam double note,
            @RequestParam String commentaire) {

        return ResponseEntity.ok(
                avisService.modifierAvis(avisId, note, commentaire)
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Page<AvisResDTO>> getAvis(
            @PathVariable String userId,
            Pageable pageable) {

        return ResponseEntity.ok(
                avisService.getAvisUtilisateur(userId, pageable)
        );
    }

    @GetMapping("/{userId}/statistiques")
    public ResponseEntity<StatistiqueAvisDTO> stats(
            @PathVariable String userId) {

        return ResponseEntity.ok(
                avisService.getStatistiques(userId)
        );
    }
}