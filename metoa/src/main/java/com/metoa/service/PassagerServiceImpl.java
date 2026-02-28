package com.metoa.service;

import com.metoa.entity.Reservation;
import com.metoa.entity.Trajet;
import com.metoa.repository.ReservationRepository;
import com.metoa.repository.TrajetRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/*Implémentation des services PassagerService
 Contient la logique métier pour chaque fonctionnalité
 */
@Service
public class PassagerServiceImpl implements PassagerService {

    private final TrajetRepository trajetRepository;
    private final ReservationRepository reservationRepository;

    public PassagerServiceImpl(TrajetRepository trajetRepository,
                               ReservationRepository reservationRepository) {
        this.trajetRepository = trajetRepository;
        this.reservationRepository = reservationRepository;
    }

    //  Suivi des trajets
    @Override
    public Optional<Trajet> suivreTrajetEnTempsReel(Long trajetId) {
        return trajetRepository.findById(trajetId);
    }

    //  Historique
    @Override
    public List<Reservation> consulterHistoriqueReservations(Long passagerId) {
        return reservationRepository.findByPassagerId(passagerId);
    }

    // Recherche de trajets
    @Override
    public List<Trajet> rechercherTrajets(String villeDepart, String villeArrivee, String dateDepart) {
        return trajetRepository.findByVilleDepartAndVilleArriveeAndDateDepart(villeDepart, villeArrivee, dateDepart);
    }

    //  Fonctionnalités avancées
    @Override
    public List<Trajet> rechercheMulticritere(String villeDepart, String villeArrivee, String dateDepart, Double maxDistance) {
        //  Ici il faudra intégrer la géolocalisation pour filtrer par distance max
        return trajetRepository.findTrajetsMulticritere(villeDepart, villeArrivee, dateDepart, maxDistance);
    }

    @Override
    public List<Trajet> rechercherProximite(Double latitude, Double longitude, Double rayonKm) {
        //  Utilisation future d'OpenStreetMap ou Google Maps API pour calculer distances
        return trajetRepository.findTrajetsProximite(latitude, longitude, rayonKm);
    }

    @Override
    public void alerteDisponibilite(Long trajetId, Long passagerId) {
        // Ici on pourra envoyer un email ou notification push quand une place se libère
    }
}
