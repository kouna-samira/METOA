package com.metoa.service;

import com.metoa.entity.Vehicule;
import com.metoa.exception.ResourceExistsException;
import com.metoa.exception.ResourceNotFoundException;
import com.metoa.repository.TrajetRepository;
import com.metoa.repository.VehiculeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VehiculeServiceImpl implements VehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final TrajetRepository trajetRepository;

    public VehiculeServiceImpl(VehiculeRepository vehiculeRepository, TrajetRepository trajetRepository) {
        this.vehiculeRepository = vehiculeRepository;
        this.trajetRepository = trajetRepository;
    }

    @Override
    public Vehicule ajouterVehicule(Vehicule vehicule) {
        // Vérifier si l'immatriculation existe déjà
        if (vehiculeRepository.existsByImmatriculation(vehicule.getImmatriculation())) {
            throw new ResourceExistsException("Un véhicule avec cette immatriculation existe déjà");
        }
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule modifierVehicule(Vehicule vehicule) {
        if (!vehiculeRepository.existsById(vehicule.getId())) {
            throw new ResourceNotFoundException("Véhicule non trouvé avec id: " + vehicule.getId());
        }
        // Optionnel : vérifier l'unicité de l'immatriculation si elle change
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public void supprimerVehicule(Long vehiculeId) {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule non trouvé avec id: " + vehiculeId));

        // Vérifier si le véhicule est utilisé dans des trajets
        boolean estUtilise = trajetRepository.existsByVehiculeId(vehiculeId);
        if (estUtilise) {
            throw new ResourceExistsException("Impossible de supprimer ce véhicule car il est associé à des trajets existants.");
        }

        vehiculeRepository.delete(vehicule);
    }

    @Override
    public Optional<Vehicule> getVehicule(Long vehiculeId) {
        return vehiculeRepository.findById(vehiculeId);
    }

    @Override
    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }
}