package com.metoa.controller;

import com.metoa.dto.TrajetReqDTO;
import com.metoa.dto.TrajetResDTO;
import com.metoa.entity.*;
import com.metoa.exception.ResourceNotFoundException;
import com.metoa.service.ConducteurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/conducteurs")
@Tag(name = "Conducteur", description = "Gestion des trajets par le conducteur")
public class ConducteurController {

    private final ConducteurService conducteurService;

    public ConducteurController(ConducteurService conducteurService) {
        this.conducteurService = conducteurService;
    }

    @PostMapping("/{conducteurId}/trajets")
    @Operation(summary = "Ajouter un nouveau trajet (brouillon)")
    public ResponseEntity<TrajetResDTO> ajouterTrajet(@PathVariable Long conducteurId,
                                                      @Valid @RequestBody TrajetReqDTO dto) {
        // Construction de l'entité Trajet à partir du DTO
        Trajet trajet = Trajet.builder()
                .conducteur(Conducteur.builder().id(conducteurId).build())
                .vehicule(Vehicule.builder().id(dto.getVehiculeId()).build())
                .villeDepart(Ville.builder().id(dto.getVilleDepartId()).build())
                .villeArrivee(Ville.builder().id(dto.getVilleArriveeId()).build())
                .dateDepart(dto.getDateDepart())
                .placesDisponibles(dto.getPlacesDisponibles())
                .prix(dto.getPrix())
                .latitudeDepart(dto.getLatitudeDepart())
                .longitudeDepart(dto.getLongitudeDepart())
                .latitudeArrivee(dto.getLatitudeArrivee())
                .longitudeArrivee(dto.getLongitudeArrivee())
                .build();
        // Le statut est mis à BROUILLON dans le service
        Trajet saved = conducteurService.ajouterTrajet(trajet);
        return new ResponseEntity<>(toDto(saved), HttpStatus.CREATED);
    }

    @PutMapping("/trajets/{trajetId}/publier")
    @Operation(summary = "Publier un trajet (passe de BROUILLON à PUBLIE)")
    public ResponseEntity<TrajetResDTO> publierTrajet(@PathVariable Long trajetId) {
        Trajet trajet = conducteurService.publierTrajet(trajetId);
        return ResponseEntity.ok(toDto(trajet));
    }

    @PutMapping("/trajets/{trajetId}")
    @Operation(summary = "Modifier un trajet existant")
    public ResponseEntity<TrajetResDTO> modifierTrajet(@PathVariable Long trajetId,
                                                       @Valid @RequestBody TrajetReqDTO dto) {
        Trajet updated = conducteurService.modifierTrajet(trajetId, dto);
        return ResponseEntity.ok(toDto(updated));
    }

    @DeleteMapping("/trajets/{trajetId}")
    @Operation(summary = "Supprimer un trajet")
    public ResponseEntity<Void> supprimerTrajet(@PathVariable Long trajetId) {
        conducteurService.supprimerTrajet(trajetId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{conducteurId}/trajets")
    @Operation(summary = "Consulter l'historique des trajets d'un conducteur")
    public List<TrajetResDTO> historiqueTrajets(@PathVariable Long conducteurId) {
        return conducteurService.consulterHistoriqueTrajets(conducteurId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @PutMapping("/reservations/{reservationId}/accepter")
    @Operation(summary = "Accepter une réservation")
    public ResponseEntity<Void> accepterReservation(@PathVariable Long reservationId) {
        conducteurService.accepterReservation(reservationId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reservations/{reservationId}/decliner")
    @Operation(summary = "Décliner une réservation")
    public ResponseEntity<Void> declinerReservation(@PathVariable Long reservationId) {
        conducteurService.declinerReservation(reservationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/trajets/{trajetId}/suivi")
    @Operation(summary = "Suivre un trajet en temps réel")
    public ResponseEntity<TrajetResDTO> suivreTrajet(@PathVariable Long trajetId) {
        Trajet trajet = conducteurService.suivreTrajetEnTempsReel(trajetId)
                .orElseThrow(() -> new ResourceNotFoundException("Trajet non trouvé avec id: " + trajetId));
        return ResponseEntity.ok(toDto(trajet));
    }

    // Méthode de conversion Trajet -> TrajetResDTO
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
        if (trajet.getReservations() != null) {
            dto.setReservationsIds(trajet.getReservations().stream().map(Reservation::getId).collect(Collectors.toList()));
        }
        return dto;
    }
}