package com.metoa.repository;

import com.metoa.entity.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

//Repository Vehicule

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {

    // Rechercher véhicule par immatriculation
    Optional<Vehicule> findByImmatriculation(String immatriculation);

    // Vérifier si immatriculation existe
    boolean existsByImmatriculation(String immatriculation);

    // Véhicules d’un conducteur
    List<Vehicule> findByConducteurId(Long conducteurId);
}
