package com.metoa.service;

import com.metoa.entity.Vehicule;
import com.metoa.repository.VehiculeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculeService {

    private final VehiculeRepository repository;

    public VehiculeService(VehiculeRepository repository){
        this.repository = repository;
    }

    // Enregistrer véhicule
    public Vehicule save(Vehicule v){
        return repository.save(v);
    }

    // Tous les véhicules
    public List<Vehicule> getAll(){
        return repository.findAll();
    }

    // Recherche par ID
    public Optional<Vehicule> getById(Long id){
        return repository.findById(id);
    }

    // Suppression
    public void delete(Long id){
        repository.deleteById(id);
    }

    // Récupérer véhicules d’un conducteur
    public List<Vehicule> findByConducteur(Long conducteurId){
        return repository.findByConducteurId(conducteurId);
    }
}
