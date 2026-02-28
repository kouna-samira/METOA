package com.metoa.service;

import com.metoa.entity.Trajet;
import com.metoa.repository.TrajetRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TrajetServiceImpl implements TrajetService {

    private final TrajetRepository trajetRepository;

    public TrajetServiceImpl(TrajetRepository trajetRepository) {
        this.trajetRepository = trajetRepository;
    }

    @Override
    public Trajet creerTrajet(Trajet trajet) {
        return trajetRepository.save(trajet);
    }

    @Override
    public Trajet modifierTrajet(Trajet trajet) {
        return trajetRepository.save(trajet);
    }

    @Override
    public void supprimerTrajet(Long trajetId) {
        trajetRepository.deleteById(trajetId);
    }

    @Override
    public Optional<Trajet> getTrajet(Long trajetId) {
        return trajetRepository.findById(trajetId);
    }

    @Override
    public List<Trajet> getAllTrajets() {
        return trajetRepository.findAll();
    }
/*
    //IMPLÉMENTATIONS AVANCÉES

// Publier un trajet

@Override
public Trajet publierTrajet(Long trajetId){
    Trajet trajet = trajetRepository.findById(trajetId)
        .orElseThrow(() -> new RuntimeException("Trajet introuvable"));

    trajet.setStatut("PUBLIE"); // statut à prévoir dans entité
    return trajetRepository.save(trajet);
}


// Recherche multicritère intelligente
@Override
public List<Trajet> rechercherTrajetsMulticritere(String villeDepart, String villeArrivee, String dateDepart, Double maxDistance){
    return trajetRepository.findTrajetsMulticritere(villeDepart, villeArrivee, dateDepart, maxDistance);
}



// Recherche par proximité GPS

@Override
public List<Trajet> rechercherTrajetsProximite(Double latitude, Double longitude, Double rayonKm){
    return trajetRepository.findTrajetsProximite(latitude, longitude, rayonKm);
}



// Suivi temps réel d’un trajet

@Override
public Trajet suivreTrajetTempsReel(Long trajetId){
    return trajetRepository.findById(trajetId)
        .orElseThrow(() -> new RuntimeException("Trajet non trouvé"));
}



// Historique conducteur

@Override
public List<Trajet> historiqueConducteur(Long conducteurId){
    return trajetRepository.findByConducteurId(conducteurId);
}



// Historique passager (nécessite table réservation)

@Override
public List<Trajet> historiquePassager(Long passagerId){
    // nécessite repository réservation
    return reservationRepository.findTrajetsByPassager(passagerId);
}



// Suggestion trajets similaires

@Override
public List<Trajet> suggererTrajetsSimilaires(Long trajetId){
    Trajet trajet = trajetRepository.findById(trajetId)
        .orElseThrow(() -> new RuntimeException("Trajet introuvable"));

    return trajetRepository.findByVilleDepartAndVilleArriveeIgnoreCase(
        trajet.getVilleDepart(),
        trajet.getVilleArrivee()
    );
}



// Vérifier places restantes

@Override
public boolean verifierDisponibilite(Long trajetId){
    Trajet trajet = trajetRepository.findById(trajetId)
        .orElseThrow(() -> new RuntimeException("Trajet introuvable"));

    return trajet.getPlacesDisponibles();
}



// Alerte disponibilité trajet

@Override
public void activerAlerteDisponibilite(String villeDepart, String villeArrivee){
}*/

}
