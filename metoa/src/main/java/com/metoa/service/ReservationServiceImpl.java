package com.metoa.service;

import com.metoa.entity.Passager;
import com.metoa.entity.Reservation;
import com.metoa.entity.ReservationStatut;
import com.metoa.entity.Trajet;
import com.metoa.exception.ResourceNotFoundException;
import com.metoa.repository.PassagerRepository;
import com.metoa.repository.ReservationRepository;
import com.metoa.repository.TrajetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final TrajetRepository trajetRepository;
    private final PassagerRepository passagerRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  TrajetRepository trajetRepository,
                                  PassagerRepository passagerRepository) {
        this.reservationRepository = reservationRepository;
        this.trajetRepository = trajetRepository;
        this.passagerRepository = passagerRepository;
    }

    @Override
    @Transactional
    public Reservation creerReservation(Reservation reservation) {
        // Vérifier que le passager existe
        Passager passager = passagerRepository.findById(reservation.getPassager().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Passager non trouvé avec id: " + reservation.getPassager().getId()));

        // Vérifier que le trajet existe
        Trajet trajet = trajetRepository.findById(reservation.getTrajet().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Trajet non trouvé avec id: " + reservation.getTrajet().getId()));

        // Vérifier que le trajet a assez de places (optionnel)
        if (trajet.getPlacesDisponibles() < reservation.getPlacesReservees()) {
            throw new RuntimeException("Pas assez de places disponibles pour ce trajet");
        }

        // Construire la réservation avec les entités complètes
        Reservation nouvelleReservation = Reservation.builder()
                .passager(passager)
                .trajet(trajet)
                .placesReservees(reservation.getPlacesReservees())
                .statut(ReservationStatut.EN_ATTENTE)
                .dateReservation(LocalDateTime.now())
                .build();

        // Sauvegarder
        Reservation saved = reservationRepository.save(nouvelleReservation);

        // Recharger avec toutes les associations (grâce à @EntityGraph)
        return reservationRepository.findById(saved.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée après création"));
    }

    @Override
    @Transactional
    public void annulerReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée avec id: " + reservationId));
        reservation.setStatut(ReservationStatut.ANNULEE);
        reservationRepository.save(reservation);
    }

    @Override
    public Optional<Reservation> getReservation(Long reservationId) {
        return reservationRepository.findById(reservationId);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
}