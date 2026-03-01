package com.metoa.controller;

import com.metoa.dto.VilleReqDTO;
import com.metoa.dto.VilleResDTO;
import com.metoa.entity.Ville;
import com.metoa.service.VilleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/villes")
@Tag(name = "Ville", description = "Gestion des villes")
public class VilleController {

    private final VilleService villeService;

    public VilleController(VilleService villeService) {
        this.villeService = villeService;
    }

    @PostMapping
    @Operation(summary = "Ajouter une ville")
    public ResponseEntity<VilleResDTO> ajouterVille(@Valid @RequestBody VilleReqDTO dto) {
        Ville ville = Ville.builder()
                .nom(dto.getNom())
                .region(dto.getRegion())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
        Ville saved = villeService.ajouterVille(ville);
        return new ResponseEntity<>(toDto(saved), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Lister toutes les villes")
    public List<VilleResDTO> getAllVilles() {
        return villeService.getAllVilles().stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une ville par son ID")
    public ResponseEntity<VilleResDTO> getVille(@PathVariable Long id) {
        Ville ville = villeService.getVille(id).orElseThrow(() -> new RuntimeException("Ville non trouvée"));
        return ResponseEntity.ok(toDto(ville));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier une ville")
    public ResponseEntity<VilleResDTO> modifierVille(@PathVariable Long id, @Valid @RequestBody VilleReqDTO dto) {
        Ville ville = Ville.builder()
                .id(id)
                .nom(dto.getNom())
                .region(dto.getRegion())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();
        Ville updated = villeService.modifierVille(ville);
        return ResponseEntity.ok(toDto(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une ville")
    public ResponseEntity<Void> supprimerVille(@PathVariable Long id) {
        villeService.supprimerVille(id);
        return ResponseEntity.noContent().build();
    }

    private VilleResDTO toDto(Ville ville) {
        VilleResDTO dto = new VilleResDTO();
        dto.setId(ville.getId());
        dto.setNom(ville.getNom());
        dto.setRegion(ville.getRegion());
        dto.setLatitude(ville.getLatitude());
        dto.setLongitude(ville.getLongitude());
        return dto;
    }
}