package com.metoa.service;

import com.metoa.entity.Conducteur;
import com.metoa.repository.ConducteurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Indique à Spring que cette classe contient de la logique métier
public class ConducteurService {

    // Injection du repository (accès base de données)
    private final ConducteurRepository repository;

    // Constructeur → Spring injecte automatiquement le repository
    public ConducteurService(ConducteurRepository repository){
        this.repository = repository;
    }

    // Enregistrer un conducteur (CREATE / UPDATE)
    public Conducteur save(Conducteur conducteur){
        return repository.save(conducteur);
    }

    // Récupérer tous les conducteurs
    public List<Conducteur> getAll(){
        return repository.findAll();
    }

    // Récupérer un conducteur par ID
    public Optional<Conducteur> getById(Long id){
        return repository.findById(id);
    }

    // Supprimer un conducteur par ID
    public void delete(Long id){
        repository.deleteById(id);
    }

    // Recherche métier : trouver par email
    public Optional<Conducteur> findByEmail(String email){
        return repository.findByEmail(email);
    }

    // Vérifier si email existe déjà
    public boolean emailExiste(String email){
        return repository.existsByEmail(email);
    }

    // Recherche par nom + prénom (ignore majuscules)
    public Optional<Conducteur> findByNomPrenom(String nom,String prenom){
        return repository.findByNomAndPrenomAllIgnoreCase(nom,prenom);
    }

    // Recherche partielle par nom
    public List<Conducteur> searchNom(String nom){
        return repository.findByNomContainingIgnoreCase(nom);
    }

}

