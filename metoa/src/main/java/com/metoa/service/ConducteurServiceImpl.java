package com.metoa.service;

import com.metoa.dto.TrajetReqDTO;
import com.metoa.entity.*;
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
        trajet.setStatut(StatutTrajet.BROUILLON);
        return trajetRepository.save(trajet);
    }

    @Override
    public Trajet publierTrajet(Long trajetId) {
        Trajet trajet = trajetRepository.findById(trajetId)
                .orElseThrow(() -> new ResourceNotFoundException("Trajet non trouvé avec id: " + trajetId));
        trajet.setStatut(StatutTrajet.PUBLIE);
        return trajetRepository.save(trajet);
    }

    @Override
    public Trajet modifierTrajet(Long trajetId, TrajetReqDTO dto) {
        Trajet trajet = trajetRepository.findById(trajetId)
                .orElseThrow(() -> new ResourceNotFoundException("Trajet non trouvé avec id: " + trajetId));

        // Mise à jour des champs (on suppose que les ID fournis sont valides)
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

        // Le statut reste inchangé
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