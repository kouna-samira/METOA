package com.groupe2.METOA.service;

import com.metoa.entity.Reservation;
import com.metoa.entity.Trajet;
import java.util.List;
import java.util.Optional;

public interface PassagerService {

    // Réservation
    Reservation creerReservation(Reservation reservation);
    void annulerReservation(Long reservationId);

    // Suivi
    Optional<Trajet> suivreTrajetEnTempsReel(Long trajetId);

    // Historique
    List<Reservation> consulterHistoriqueReservations(Long passagerId);

    // Recherche
    List<Trajet> rechercherTrajets(String villeDepart, String villeArrivee, String dateDepart);
    List<Trajet> rechercheMulticritere(String villeDepart, String villeArrivee, String dateDepart,
                                       Double maxDistance, Double latitude, Double longitude);
    List<Trajet> rechercherProximite(Double latitude, Double longitude, Double rayonKm);

    // Alerte (à implémenter)
    void alerteDisponibilite(Long trajetId, Long passagerId);
}
