package com.metoa.service;

import com.metoa.entity.Reservation;
import com.metoa.entity.Trajet;
import java.util.List;
import java.util.Optional;

/*Interface pour les services liés au passager
  Contient toutes les fonctionnalités que le passager peut utiliser
 */
public interface PassagerService {

    // Suivi des trajets
    Optional<Trajet> suivreTrajetEnTempsReel(Long trajetId); // Suivi du trajet en temps réel

    // Historique
    List<Reservation> consulterHistoriqueReservations(Long passagerId);

    // Recherche de trajets
    List<Trajet> rechercherTrajets(String villeDepart, String villeArrivee, String dateDepart);

    // Fonctionnalités avancées (à compléter plus tard)
    List<Trajet> rechercheMulticritere(String villeDepart, String villeArrivee, String dateDepart, Double maxDistance);
    List<Trajet> rechercherProximite(Double latitude, Double longitude, Double rayonKm);
    void alerteDisponibilite(Long trajetId, Long passagerId);
}
