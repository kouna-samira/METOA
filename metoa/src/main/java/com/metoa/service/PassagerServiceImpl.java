package com.metoa.service;

import com.metoa.entity.*;
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
@Transactional
public class PassagerServiceImpl implements PassagerService {

    private final TrajetRepository trajetRepository;
    private final ReservationRepository reservationRepository;
    private final PassagerRepository passagerRepository;

    public PassagerServiceImpl(TrajetRepository trajetRepository,
                               ReservationRepository reservationRepository,
                               PassagerRepository passagerRepository) {
        this.trajetRepository = trajetRepository;
        this.reservationRepository = reservationRepository;
        this.passagerRepository = passagerRepository;
    }

    @Override
    public Reservation creerReservation(Reservation reservation) {
        // Vérifier que le passager existe
        Passager passager = passagerRepository.findById(reservation.getPassager().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Passager non trouvé avec id: " + reservation.getPassager().getId()));
        // Vérifier que le trajet existe et a assez de places
        Trajet trajet = trajetRepository.findById(reservation.getTrajet().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Trajet non trouvé avec id: " + reservation.getTrajet().getId()));
        if (trajet.getPlacesDisponibles() < reservation.getPlacesReservees()) {
            throw new RuntimeException("Pas assez de places disponibles pour ce trajet");
        }
        // Initialiser les champs
        reservation.setStatut(ReservationStatut.EN_ATTENTE);
        reservation.setDateReservation(LocalDateTime.now());
        return reservationRepository.save(reservation);
    }

    @Override
    public void annulerReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation non trouvée avec id: " + reservationId));
        reservation.setStatut(ReservationStatut.ANNULEE);
        reservationRepository.save(reservation);
    }

    @Override
    public Optional<Trajet> suivreTrajetEnTempsReel(Long trajetId) {
        return trajetRepository.findById(trajetId);
    }

    @Override
    public List<Reservation> consulterHistoriqueReservations(Long passagerId) {
        return reservationRepository.findByPassagerId(passagerId);
    }

    @Override
    public List<Trajet> rechercherTrajets(String villeDepart, String villeArrivee, String dateDepart) {
        // Simplification : recherche par noms de villes, ignore la date pour l'instant
        return trajetRepository.findByVilleDepartNomAndVilleArriveeNom(villeDepart, villeArrivee);
    }

    @Override
    public List<Trajet> rechercheMulticritere(String villeDepart, String villeArrivee, String dateDepart,
                                              Double maxDistance, Double latitude, Double longitude) {
        LocalDateTime date = null;
        if (dateDepart != null && !dateDepart.isEmpty()) {
            date = LocalDateTime.parse(dateDepart); // Attention: gérer les exceptions de parsing
        }
        return trajetRepository.rechercheMulticritere(villeDepart, villeArrivee, date, latitude, longitude, maxDistance);
    }

    @Override
    public List<Trajet> rechercherProximite(Double latitude, Double longitude, Double rayonKm) {
        return trajetRepository.findProximite(latitude, longitude, rayonKm);
    }

    @Override
    public void alerteDisponibilite(Long trajetId, Long passagerId) {
        // À implémenter plus tard (envoi de notification)
    }
}