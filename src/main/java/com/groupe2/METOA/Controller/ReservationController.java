package com.groupe2.METOA.Controller;

import com.groupe2.METOA.Dto.ReservationResDto;
import com.groupe2.METOA.Dto.ReservationSuggestionDto;
import com.groupe2.METOA.Entity.Reservation;
import com.groupe2.METOA.Service.Reservation.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    // Pagination
    @GetMapping
    public Page<ReservationResDto> getAllReservations(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {

        return reservationService.getReservations(page, size);
    }



    // Ajouter une réservation
    @PostMapping(path = "/add")
    public void addReservation(@RequestParam String clientId,
                               @RequestParam String trajetId) {
        reservationService.addReservation(clientId, trajetId);
    }

    // Obtenir une réservation par id
    @GetMapping(path = "/get_reservation_by_id/{id}")
    public ReservationResDto getReservationById(@PathVariable String id) {
        return reservationService.getReservationById(id);
    }

    // Obtenir toutes les réservations
    @GetMapping(path = "/get_all_reservation")
    public List<ReservationResDto> getAllReservations() {
        return reservationService.getAllReservations();
    }

    // Supprimer une réservation
    @DeleteMapping(path = "/delete_/{id}")
    public void deleteReservation(@PathVariable String id) {
        reservationService.deleteReservation(id);
    }

    //supression et suggestion
    @PatchMapping("/annuler suggestion/{id}")
    public ResponseEntity<ReservationSuggestionDto> annulerEtSuggere(@PathVariable String id){
        return ResponseEntity.ok(reservationService.annulerEtSuggere(id));
    }
    @GetMapping("/covoiturage/{idTrajet}")
    public ResponseEntity<List<Reservation>> getReservationsPourCovoiturage(@PathVariable String idTrajet) {
        List<Reservation> reservations = reservationService.getReservationsPourCovoiturage(idTrajet);
        return reservations.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(reservations);
    }
}
