package com.groupe2.METOA.controller;


import com.metoa.dto.ReservationReqDTO;
import com.metoa.dto.ReservationResDTO;
import com.metoa.dto.TrajetResDTO;
import com.metoa.entity.*;
import com.metoa.exception.ResourceNotFoundException;
import com.metoa.service.PassagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/passagers")
@Tag(name = "Passager", description = "Gestion des réservations et recherche de trajets")
public class PassagerController {

    private final PassagerService passagerService;

    public PassagerController(PassagerService passagerService) {
        this.passagerService = passagerService;
    }

    @PostMapping("/{passagerId}/reservations")
    @Operation(summary = "Créer une réservation")
    public ResponseEntity<ReservationResDTO> creerReservation(@PathVariable Long passagerId,
                                                              @Valid @RequestBody ReservationReqDTO dto) {
        Reservation reservation = Reservation.builder()
                .passager(Passager.builder().id(passagerId).build())
                .trajet(Trajet.builder().id(dto.getTrajetId()).build())
                .placesReservees(dto.getPlacesReservees())
                .build();
        Reservation saved = passagerService.creerReservation(reservation);
        return new ResponseEntity<>(toDto(saved), HttpStatus.CREATED);
    }

    @DeleteMapping("/reservations/{reservationId}")
    @Operation(summary = "Annuler une réservation")
    public ResponseEntity<Void> annulerReservation(@PathVariable Long reservationId) {
        passagerService.annulerReservation(reservationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{passagerId}/reservations")
    @Operation(summary = "Historique des réservations")
    public List<ReservationResDTO> historiqueReservations(@PathVariable Long passagerId) {
        return passagerService.consulterHistoriqueReservations(passagerId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/trajets/recherche")
    @Operation(summary = "Rechercher des trajets par ville de départ et d'arrivée")
    public List<TrajetResDTO> rechercherTrajets(@RequestParam String depart,
                                                @RequestParam String arrivee,
                                                @RequestParam(required = false) String date) {
        return passagerService.rechercherTrajets(depart, arrivee, date)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/trajets/recherche-multicritere")
    @Operation(summary = "Recherche multicritère avec distance maximale")
    public List<TrajetResDTO> rechercheMulticritere(
            @RequestParam(required = false) String depart,
            @RequestParam(required = false) String arrivee,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) Double maxDistance,
            @RequestParam Double latitude,
            @RequestParam Double longitude) {
        return passagerService.rechercheMulticritere(depart, arrivee, date, maxDistance, latitude, longitude)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/trajets/proximite")
    @Operation(summary = "Recherche par proximité géographique")
    public List<TrajetResDTO> rechercherProximite(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam Double rayonKm) {
        return passagerService.rechercherProximite(latitude, longitude, rayonKm)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/trajets/{trajetId}/suivi")
    @Operation(summary = "Suivre un trajet en temps réel")
    public ResponseEntity<TrajetResDTO> suivreTrajet(@PathVariable Long trajetId) {
        Trajet trajet = passagerService.suivreTrajetEnTempsReel(trajetId)
                .orElseThrow(() -> new ResourceNotFoundException("Trajet non trouvé avec id: " + trajetId));
        return ResponseEntity.ok(toDto(trajet));
    }

    // Conversion Trajet -> TrajetResDTO (identique à celle du ConducteurController, à mutualiser)
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

    private ReservationResDTO toDto(Reservation reservation) {
        ReservationResDTO dto = new ReservationResDTO();
        dto.setId(reservation.getId());
        dto.setPassagerId(reservation.getPassager().getId());
        dto.setPassagerNom(reservation.getPassager().getNom() + " " + reservation.getPassager().getPrenom());
        dto.setTrajetId(reservation.getTrajet().getId());
        dto.setTrajetDescription(reservation.getTrajet().getVilleDepart().getNom() + " → " + reservation.getTrajet().getVilleArrivee().getNom());
        dto.setPlacesReservees(reservation.getPlacesReservees());
        dto.setStatut(reservation.getStatut().toString());
        dto.setDateReservation(reservation.getDateReservation());
        return dto;
    }
}