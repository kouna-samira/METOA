package com.groupe2.METOA.service;

import com.metoa.entity.Vehicule;
import java.util.List;
import java.util.Optional;

public interface VehiculeService {
    Vehicule ajouterVehicule(Vehicule vehicule);
    Vehicule modifierVehicule(Vehicule vehicule);
    void supprimerVehicule(Long vehiculeId);
    Optional<Vehicule> getVehicule(Long vehiculeId);
    List<Vehicule> getAllVehicules();
}