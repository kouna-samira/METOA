package com.metoa.controller;

import com.metoa.dto.ReservationReqDTO;
import com.metoa.dto.ReservationResDTO;
import com.metoa.entity.Passager;
import com.metoa.entity.Reservation;
import com.metoa.entity.ReservationStatut;
import com.metoa.entity.Trajet;
import com.metoa.exception.ResourceNotFoundException;
import com.metoa.service.ReservationService;
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
@RequestMapping("/api/reservations")
@Tag(name = "Réservation", description = "Gestion des réservations (création, annulation, consultation)")
public class ReservationController {

    private final ReservationService reservationService;

    // Injection par constructeur
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle réservation")
    public ResponseEntity<ReservationResDTO> creerReservation(@Valid @RequestBody ReservationReqDTO dto) {
        // Conversion DTO → Entité (manuel, idéalement avec MapStruct)
        Reservation reservation = new Reservation();
        // On ne set que les IDs, les entités seront chargées par le service si besoin
        reservation.setPassager(Passager.builder().id(dto.getPassagerId()).build());
        reservation.setTrajet(Trajet.builder().id(dto.getTrajetId()).build());
        reservation.setPlacesReservees(dto.getPlacesReservees());
        reservation.setStatut(ReservationStatut.EN_ATTENTE);
        reservation.setDateReservation(LocalDateTime.now());

        Reservation saved = reservationService.creerReservation(reservation);
        return new ResponseEntity<>(toDto(saved), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Annuler une réservation (changement de statut à ANNULEE)")
    public ResponseEntity<Void> annulerReservation(@PathVariable Long id) {
        reservationService.annulerReservation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une réservation par son ID")
    public ResponseEntity<ReservationResDTO> getReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservation(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée avec l'id : " + id));
        return ResponseEntity.ok(toDto(reservation));
    }

    @GetMapping
    @Operation(summary = "Lister toutes les réservations")
    public List<ReservationResDTO> getAllReservations() {
        return reservationService.getAllReservations().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Méthode privée de conversion entité → DTO
    private ReservationResDTO toDto(Reservation reservation) {
        ReservationResDTO dto = new ReservationResDTO();
        dto.setId(reservation.getId());
        dto.setPassagerId(reservation.getPassager().getId());
        dto.setPassagerNom(reservation.getPassager().getNom() + " " + reservation.getPassager().getPrenom());
        dto.setTrajetId(reservation.getTrajet().getId());
        dto.setTrajetDescription(reservation.getTrajet().getVilleDepart().getNom() + " → " +
                reservation.getTrajet().getVilleArrivee().getNom());
        dto.setPlacesReservees(reservation.getPlacesReservees());
        dto.setStatut(reservation.getStatut().toString());
        dto.setDateReservation(reservation.getDateReservation());
        return dto;
    }
}