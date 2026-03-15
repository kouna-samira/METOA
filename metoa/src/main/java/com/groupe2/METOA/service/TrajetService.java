package com.groupe2.METOA.service;

import com.metoa.entity.Trajet;
import java.util.List;
import java.util.Optional;

public interface TrajetService {
    Trajet creerTrajet(Trajet trajet);
    Trajet modifierTrajet(Trajet trajet);
    void supprimerTrajet(Long trajetId);
    Optional<Trajet> getTrajet(Long trajetId);
    List<Trajet> getAllTrajets();
}