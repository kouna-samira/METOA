package com.metoa.service;

import com.metoa.entity.Reservation;
import com.metoa.entity.Trajet;
import com.metoa.repository.ReservationRepository;
import com.metoa.repository.TrajetRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/*Implémentation des services ConducteurService
  Contient la logique métier pour chaque fonctionnalité
  */
@Service
public class ConducteurServiceImpl implements ConducteurService {

    private final TrajetRepository trajetRepository;
    private final ReservationRepository reservationRepository;

    public ConducteurServiceImpl(TrajetRepository trajetRepository,
                                 ReservationRepository reservationRepository) {
        this.trajetRepository = trajetRepository;
        this.reservationRepository = reservationRepository;
    }

    // Gestion des trajets
    @Override
    public Trajet ajouterTrajet(Trajet trajet) {
        // Logique pour ajouter un trajet avec coordonnées GPS pour la carte
        // Assurez-vous que latitudeDepart, longitudeDepart, latitudeArrivee, longitudeArrivee sont renseignés
        return trajetRepository.save(trajet);
    }

    @Override
    public Trajet publierTrajet(Long trajetId) {
        // Logique pour publier un trajet
        Trajet trajet = trajetRepository.findById(trajetId).orElseThrow(() -> new RuntimeException("Trajet non trouvé"));
        trajet.setPublie(true); // suppose que tu as un champ boolean 'publie' dans Trajet
        return trajetRepository.save(trajet);
    }

    @Override
    public Trajet modifierTrajet(Trajet trajet) {
        // Logique pour modifier un trajet existant
        return trajetRepository.save(trajet);
    }

    @Override
    public void supprimerTrajet(Long trajetId) {
        trajetRepository.deleteById(trajetId);
    }

    // Gestion des réservations
    @Override
    public Reservation accepterReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));
        reservation.setAcceptee(true);
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation declinerReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));
        reservation.setAcceptee(false);
        return reservationRepository.save(reservation);
    }

    // Suivi des trajets
    @Override
    public Optional<Trajet> suivreTrajetEnTempsReel(Long trajetId) {
        // Pour le suivi réel : il faudra plus tard intégrer WebSocket ou polling + API de géolocalisation
        return trajetRepository.findById(trajetId);
    }

    //Historique
    @Override
    public List<Trajet> consulterHistoriqueTrajets(Long conducteurId) {
        return trajetRepository.findByConducteurId(conducteurId);
    }
}
