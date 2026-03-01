package com.metoa.service;

import com.metoa.entity.Vehicule;
import com.metoa.exception.ResourceNotFoundException;
import com.metoa.repository.VehiculeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VehiculeServiceImpl implements VehiculeService {

    private final VehiculeRepository vehiculeRepository;

    public VehiculeServiceImpl(VehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
    }

    @Override
    public Vehicule ajouterVehicule(Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule modifierVehicule(Vehicule vehicule) {
        if (!vehiculeRepository.existsById(vehicule.getId())) {
            throw new ResourceNotFoundException("Véhicule non trouvé avec id: " + vehicule.getId());
        }
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public void supprimerVehicule(Long vehiculeId) {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule non trouvé avec id: " + vehiculeId));
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