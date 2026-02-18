package com.metoa.service;

import com.metoa.entity.Reservation;
import com.metoa.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository){
        this.repository = repository;
    }

    // Enregistrer réservation
    public Reservation save(Reservation r){
        return repository.save(r);
    }

    // Toutes les réservations
    public List<Reservation> getAll(){
        return repository.findAll();
    }

    // Recherche par ID
    public Optional<Reservation> getById(Long id){
        return repository.findById(id);
    }

    // Supprimer réservation
    public void delete(Long id){
        repository.deleteById(id);
    }

    // Réservations d’un passager
    public List<Reservation> findByPassager(Long passagerId){
        return repository.findByPassagerId(passagerId);
    }

    // Réservations d’un trajet
    public List<Reservation> findByTrajet(Long trajetId){
        return repository.findByTrajetId(trajetId);
    }
}
