package com.metoa.service;

import com.metoa.entity.Trajet;
import com.metoa.repository.TrajetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrajetService {

    private final TrajetRepository repository;

    public TrajetService(TrajetRepository repository){
        this.repository = repository;
    }

    // Créer ou modifier trajet
    public Trajet save(Trajet trajet){
        return repository.save(trajet);
    }

    // Tous les trajets
    public List<Trajet> getAll(){
        return repository.findAll();
    }

    // Recherche par ID
    public Optional<Trajet> getById(Long id){
        return repository.findById(id);
    }

    // Suppression trajet
    public void delete(Long id){
        repository.deleteById(id);
    }

    // Recherche par ville départ + arrivée
    public List<Trajet> search(String depart,String arrivee){
        return repository.findByVilleDepartAndVilleArriveeIgnoreCase(depart,arrivee);
    }

    // Trajets d’un conducteur
    public List<Trajet> findByConducteur(Long conducteurId){
        return repository.findByConducteurId(conducteurId);
    }
}

