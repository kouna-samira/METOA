package com.metoa.service;

import com.metoa.entity.Ville;
import com.metoa.repository.VilleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Indique que cette classe contient de la logique métier
public class VilleService {

    // Accès base de données
    private final VilleRepository repository;

    // Injection automatique du repository par Spring
    public VilleService(VilleRepository repository){
        this.repository = repository;
    }

    // Enregistrer une ville (CREATE ou UPDATE)
    public Ville save(Ville ville){
        return repository.save(ville);
    }

    // Récupérer toutes les villes
    public List<Ville> getAll(){
        return repository.findAll();
    }

    // Récupérer une ville par ID
    public Optional<Ville> getById(Long id){
        return repository.findById(id);
    }

    // Supprimer une ville
    public void delete(Long id){
        repository.deleteById(id);
    }

    // Recherche par nom exact (ignore majuscules/minuscules)
    public Optional<Ville> findByNom(String nom){
        return repository.findByNomIgnoreCase(nom);
    }

    // Recherche partielle par nom (autocomplete par exemple)
    public List<Ville> search(String nom){
        return repository.findByNomContainingIgnoreCase(nom);
    }
}
