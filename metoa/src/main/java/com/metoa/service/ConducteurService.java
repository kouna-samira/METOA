package com.metoa.service;

import com.metoa.dto.TrajetReqDTO;
import com.metoa.entity.Reservation;
import com.metoa.entity.Trajet;
import java.util.List;
import java.util.Optional;

public interface ConducteurService {

    Trajet ajouterTrajet(Trajet trajet);
    Trajet publierTrajet(Long trajetId);
    Trajet modifierTrajet(Long trajetId, TrajetReqDTO dto); // Nouvelle signature
    void supprimerTrajet(Long trajetId);

    Reservation accepterReservation(Long reservationId);
    Reservation declinerReservation(Long reservationId);

    Optional<Trajet> suivreTrajetEnTempsReel(Long trajetId);

    List<Trajet> consulterHistoriqueTrajets(Long conducteurId);
}