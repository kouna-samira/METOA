package com.metoa.controller;

import com.metoa.dto.VehiculeReqDTO;
import com.metoa.dto.VehiculeResDTO;
import com.metoa.entity.Conducteur;
import com.metoa.entity.Vehicule;
import com.metoa.exception.ResourceNotFoundException;
import com.metoa.service.VehiculeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicules")
@Tag(name = "Véhicule", description = "Gestion des véhicules (CRUD)")
public class VehiculeController {

    private final VehiculeService vehiculeService;

    public VehiculeController(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    @PostMapping
    @Operation(summary = "Ajouter un nouveau véhicule")
    public ResponseEntity<VehiculeResDTO> ajouterVehicule(@Valid @RequestBody VehiculeReqDTO dto) {
        Vehicule vehicule = new Vehicule();
        vehicule.setMarque(dto.getMarque());
        vehicule.setModele(dto.getModele());
        vehicule.setCouleur(dto.getCouleur());
        vehicule.setImmatriculation(dto.getImmatriculation());
        vehicule.setNombrePlaces(dto.getNombrePlaces());
        // Association avec le conducteur (si conducteurId fourni)
        if (dto.getConducteurId() != null) {
            vehicule.setConducteur(Conducteur.builder().id(dto.getConducteurId()).build());
        }

        Vehicule saved = vehiculeService.ajouterVehicule(vehicule);
        return new ResponseEntity<>(toDto(saved), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un véhicule")
    public ResponseEntity<VehiculeResDTO> modifierVehicule(@PathVariable Long id, @Valid @RequestBody VehiculeReqDTO dto) {
        Vehicule existing = vehiculeService.getVehicule(id)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule non trouvé avec l'id : " + id));

        existing.setMarque(dto.getMarque());
        existing.setModele(dto.getModele());
        existing.setCouleur(dto.getCouleur());
        existing.setImmatriculation(dto.getImmatriculation());
        existing.setNombrePlaces(dto.getNombrePlaces());
        if (dto.getConducteurId() != null) {
            existing.setConducteur(Conducteur.builder().id(dto.getConducteurId()).build());
        }

        Vehicule updated = vehiculeService.modifierVehicule(existing);
        return ResponseEntity.ok(toDto(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un véhicule")
    public ResponseEntity<Void> supprimerVehicule(@PathVariable Long id) {
        vehiculeService.supprimerVehicule(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un véhicule par ID")
    public ResponseEntity<VehiculeResDTO> getVehicule(@PathVariable Long id) {
        Vehicule vehicule = vehiculeService.getVehicule(id)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule non trouvé avec l'id : " + id));
        return ResponseEntity.ok(toDto(vehicule));
    }

    @GetMapping
    @Operation(summary = "Lister tous les véhicules")
    public List<VehiculeResDTO> getAllVehicules() {
        return vehiculeService.getAllVehicules().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private VehiculeResDTO toDto(Vehicule vehicule) {
        VehiculeResDTO dto = new VehiculeResDTO();
        dto.setId(vehicule.getId());
        dto.setMarque(vehicule.getMarque());
        dto.setModele(vehicule.getModele());
        dto.setCouleur(vehicule.getCouleur());
        dto.setImmatriculation(vehicule.getImmatriculation());
        dto.setNombrePlaces(vehicule.getNombrePlaces());
        if (vehicule.getConducteur() != null) {
            dto.setConducteurId(vehicule.getConducteur().getId());
        }
        return dto;
    }
}