package com.groupe2.METOA.gestionProfilUtiisateur.controller;


import com.groupe2.METOA.gestionProfilUtiisateur.dto.historiqueTrajetDTO.HistoriqueTrajetResDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.historiqueTrajet.StatusTrajet;
import com.groupe2.METOA.gestionProfilUtiisateur.service.historqueTrajet.HistoriqueTrajetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Trajets", description = "consulter l' historique de trajet")
@RestController
@RequestMapping(path = "api/v1/historiqueTrajet")
public class HistoriqueTrajetController {
    private  final HistoriqueTrajetService historiqueTrajetService;

    public HistoriqueTrajetController(HistoriqueTrajetService historiqueTrajetService) {
        this.historiqueTrajetService = historiqueTrajetService;
    }
    @Operation(summary = "Afficher tous les trajets d’un utilisateur par statut")
    @GetMapping("/user/{idUser}/status/{statusTrajet}")
    public ResponseEntity<List<HistoriqueTrajetResDTO>> getTrajetByUserIdAndStatusTrajet(@PathVariable @Valid String idUser, @RequestBody @Valid StatusTrajet statusTrajet){
        return ResponseEntity.status(200).body(this.historiqueTrajetService.getFindByUserIdAndStatusTrajet(idUser ,statusTrajet));
    }

}
