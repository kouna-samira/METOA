package com.metoa.controller;

import com.metoa.dto.TrajetReqDTO;
import com.metoa.dto.TrajetResDTO;
import com.metoa.entity.Conducteur;
import com.metoa.entity.Trajet;
import com.metoa.entity.Vehicule;
import com.metoa.entity.Ville;
import com.metoa.exception.ResourceNotFoundException;
import com.metoa.service.TrajetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trajets")
@Tag(name = "Trajet", description = "Gestion des trajets (CRUD de base)")
public class TrajetController {

    private final TrajetService trajetService;

    public TrajetController(TrajetService trajetService) {
        this.trajetService = trajetService;
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau trajet")
    public ResponseEntity<TrajetResDTO> creerTrajet(@Valid @RequestBody TrajetReqDTO dto) {
        // Conversion DTO → Entité (simplifiée, à améliorer avec MapStruct)
        Trajet trajet = new Trajet();
        trajet.setConducteur(Conducteur.builder().id(dto.getConducteurId()).build());
        trajet.setVehicule(Vehicule.builder().id(dto.getVehiculeId()).build());
        trajet.setVilleDepart(Ville.builder().id(dto.getVilleDepartId()).build());
        trajet.setVilleArrivee(Ville.builder().id(dto.getVilleArriveeId()).build());
        trajet.setDateDepart(dto.getDateDepart());
        trajet.setPlacesDisponibles(dto.getPlacesDisponibles());
        trajet.setPrix(dto.getPrix());
        trajet.setLatitudeDepart(dto.getLatitudeDepart());
        trajet.setLongitudeDepart(dto.getLongitudeDepart());
        trajet.setLatitudeArrivee(dto.getLatitudeArrivee());
        trajet.setLongitudeArrivee(dto.getLongitudeArrivee());
        // Par défaut, le statut est BROUILLON (à définir dans le service si besoin)
        // trajet.setStatut(StatutTrajet.BROUILLON);

        Trajet saved = trajetService.creerTrajet(trajet);
        return new ResponseEntity<>(toDto(saved), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un trajet existant")
    public ResponseEntity<TrajetResDTO> modifierTrajet(@PathVariable Long id, @Valid @RequestBody TrajetReqDTO dto) {
        // Vérifier que le trajet existe
        Trajet existing = trajetService.getTrajet(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trajet non trouvé avec l'id : " + id));

        // Mise à jour des champs
        existing.setConducteur(Conducteur.builder().id(dto.getConducteurId()).build());
        existing.setVehicule(Vehicule.builder().id(dto.getVehiculeId()).build());
        existing.setVilleDepart(Ville.builder().id(dto.getVilleDepartId()).build());
        existing.setVilleArrivee(Ville.builder().id(dto.getVilleArriveeId()).build());
        existing.setDateDepart(dto.getDateDepart());
        existing.setPlacesDisponibles(dto.getPlacesDisponibles());
        existing.setPrix(dto.getPrix());
        existing.setLatitudeDepart(dto.getLatitudeDepart());
        existing.setLongitudeDepart(dto.getLongitudeDepart());
        existing.setLatitudeArrivee(dto.getLatitudeArrivee());
        existing.setLongitudeArrivee(dto.getLongitudeArrivee());

        Trajet updated = trajetService.modifierTrajet(existing);
        return ResponseEntity.ok(toDto(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un trajet")
    public ResponseEntity<Void> supprimerTrajet(@PathVariable Long id) {
        trajetService.supprimerTrajet(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un trajet par son ID")
    public ResponseEntity<TrajetResDTO> getTrajet(@PathVariable Long id) {
        Trajet trajet = trajetService.getTrajet(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trajet non trouvé avec l'id : " + id));
        return ResponseEntity.ok(toDto(trajet));
    }

    @GetMapping
    @Operation(summary = "Lister tous les trajets")
    public List<TrajetResDTO> getAllTrajets() {
        return trajetService.getAllTrajets().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Méthode privée de conversion entité → DTO
    private TrajetResDTO toDto(Trajet trajet) {
        TrajetResDTO dto = new TrajetResDTO();
        dto.setId(trajet.getId());
        dto.setConducteurId(trajet.getConducteur().getId());
        dto.setConducteurNomComplet(trajet.getConducteur().getNom() + " " + trajet.getConducteur().getPrenom());
        dto.setVehiculeId(trajet.getVehicule().getId());
        dto.setVehiculeImmatriculation(trajet.getVehicule().getImmatriculation());
        dto.setVilleDepart(trajet.getVilleDepart().getNom());
        dto.setVilleArrivee(trajet.getVilleArrivee().getNom());
        dto.setDateDepart(trajet.getDateDepart());
        dto.setPlacesDisponibles(trajet.getPlacesDisponibles());
        dto.setPrix(trajet.getPrix());
        dto.setStatut(trajet.getStatut().toString());
        dto.setLatitudeDepart(trajet.getLatitudeDepart());
        dto.setLongitudeDepart(trajet.getLongitudeDepart());
        dto.setLatitudeArrivee(trajet.getLatitudeArrivee());
        dto.setLongitudeArrivee(trajet.getLongitudeArrivee());
        return dto;
    }
}