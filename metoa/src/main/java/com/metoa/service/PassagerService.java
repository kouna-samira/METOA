package com.metoa.service;

import com.metoa.entity.Passager;
import com.metoa.repository.PassagerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassagerService {

    private final PassagerRepository repository;

    public PassagerService(PassagerRepository repository){
        this.repository = repository;
    }

    // Sauvegarde passager
    public Passager save(Passager passager){
        return repository.save(passager);
    }

    // Liste complète
    public List<Passager> getAll(){
        return repository.findAll();
    }

    // Recherche par ID
    public Optional<Passager> getById(Long id){
        return repository.findById(id);
    }

    // Suppression
    public void delete(Long id){
        repository.deleteById(id);
    }

    // Recherche par email
    public Optional<Passager> findByEmail(String email){
        return repository.findByEmail(email);
    }

    // Recherche nom + prénom
    public Optional<Passager> findByNomPrenom(String nom,String prenom){
        return repository.findByNomAndPrenomAllIgnoreCase(nom,prenom);
    }

    // Recherche partielle nom
    public List<Passager> searchNom(String nom){
        return repository.findByNomContainingIgnoreCase(nom);
    }
}
