package com.metoa.service;

import com.metoa.entity.Trajet;
import java.util.List;
import java.util.Optional;

public interface TrajetService {
    Trajet creerTrajet(Trajet trajet);
    Trajet modifierTrajet(Trajet trajet);
    void supprimerTrajet(Long trajetId);
    Optional<Trajet> getTrajet(Long trajetId);
    List<Trajet> getAllTrajets();

    /*
    //FONCTIONNALITÉS AVANCÉES

// Publier un trajet (changer statut BROUILLON → PUBLIÉ)
Trajet publierTrajet(Long trajetId);

// Recherche multicritère intelligente
 List<Trajet> rechercherTrajetsMulticritere(String villeDepart, String villeArrivee, String dateDepart, Double maxDistance);

// Recherche de trajets par proximité GPS
 List<Trajet> rechercherTrajetsProximite(Double latitude, Double longitude, Double rayonKm);

// Suivi temps réel d’un trajet
 Trajet suivreTrajetTempsReel(Long trajetId);

// Historique des trajets d’un conducteur
 List<Trajet> historiqueConducteur(Long conducteurId);

// Historique des trajets d’un passager
 List<Trajet> historiquePassager(Long passagerId);

// Détection automatique trajets similaires (optimisation covoiturage)
 List<Trajet> suggererTrajetsSimilaires(Long trajetId);

// Vérification disponibilité places restantes
 boolean verifierDisponibilite(Long trajetId);

// Alerte disponibilité trajet (notification si un trajet apparaît)
 void activerAlerteDisponibilite(String villeDepart, String villeArrivee);
*/
}
