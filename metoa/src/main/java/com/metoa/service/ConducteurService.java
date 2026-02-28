package com.metoa.service;

import com.metoa.entity.Reservation;
import com.metoa.entity.Trajet;
import java.util.List;
import java.util.Optional;

/*Interface pour les services liés au conducteur
 Contient toutes les fonctionnalités que le conducteur peut utiliser
 */
public interface ConducteurService {

    // Gestion des trajets
    Trajet ajouterTrajet(Trajet trajet);
    Trajet publierTrajet(Long trajetId);
    Trajet modifierTrajet(Trajet trajet);
    void supprimerTrajet(Long trajetId);

    // Gestion des réservations
    Reservation accepterReservation(Long reservationId);
    Reservation declinerReservation(Long reservationId);

    // Suivi des trajets
    Optional<Trajet> suivreTrajetEnTempsReel(Long trajetId);

    // Historique
    List<Trajet> consulterHistoriqueTrajets(Long conducteurId);

}
