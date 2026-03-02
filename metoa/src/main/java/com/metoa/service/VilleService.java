package com.metoa.service;

import com.metoa.entity.Ville;
import java.util.List;
import java.util.Optional;

public interface VilleService {
    Ville ajouterVille(Ville ville);
    Ville modifierVille(Ville ville);
    void supprimerVille(Long villeId);
    Optional<Ville> getVille(Long villeId);
    List<Ville> getAllVilles();
}