package com.metoa.service;

import com.metoa.entity.Reservation;
import com.metoa.entity.ReservationStatut;
import com.metoa.entity.StatutTrajet;
import com.metoa.entity.Trajet;
import com.metoa.exception.ResourceNotFoundException;
import com.metoa.repository.ReservationRepository;
import com.metoa.repository.TrajetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ConducteurServiceImpl implements ConducteurService {

    private final TrajetRepository trajetRepository;
    private final ReservationRepository reservationRepository;

    public ConducteurServiceImpl(TrajetRepository trajetRepository,
                                 ReservationRepository reservationRepository) {
        this.trajetRepository = trajetRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Trajet ajouterTrajet(Trajet trajet) {
        // Le trajet est créé en brouillon par défaut
        trajet.setStatut(StatutTrajet.BROUILLON);
        return trajetRepository.save(trajet);
    }

    @Override
    public Trajet publierTrajet(Long trajetId) {
        Trajet trajet = trajetRepository.findById(trajetId)
                .orElseThrow(() -> new ResourceNotFoundException("Trajet non trouvé avec id: " + trajetId));
        trajet.setPublie(true); // ou trajet.setStatut(StatutTrajet.PUBLIE);
        return trajetRepository.save(trajet);
    }

    @Override
    public Trajet modifierTrajet(Trajet trajet) {
        // Vérifier que le trajet existe (l'id est fourni)
        if (!trajetRepository.existsById(trajet.getId())) {
            throw new ResourceNotFoundException("Trajet non trouvé avec id: " + trajet.getId());
        }
        return trajetRepository.save(trajet);
    }

    @Override
    public void supprimerTrajet(Long trajetId) {
        Trajet trajet = trajetRepository.findById(trajetId)
                .orElseThrow(() -> new ResourceNotFoundException("Trajet non trouvé avec id: " + trajetId));
        trajetRepository.delete(trajet);
    }

    @Override
    public Reservation accepterReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée avec id: " + reservationId));
        reservation.setStatut(ReservationStatut.ACCEPTEE);
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation declinerReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée avec id: " + reservationId));
        reservation.setStatut(ReservationStatut.REFUSEE);
        return reservationRepository.save(reservation);
    }

    @Override
    public Optional<Trajet> suivreTrajetEnTempsReel(Long trajetId) {
        return trajetRepository.findById(trajetId);
    }

    @Override
    public List<Trajet> consulterHistoriqueTrajets(Long conducteurId) {
        return trajetRepository.findByConducteurId(conducteurId);
    }
}