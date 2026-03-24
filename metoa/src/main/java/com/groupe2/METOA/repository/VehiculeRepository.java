package com.groupe2.METOA.repository;

import com.metoa.entity.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {

    // Recherche par immatriculation unique
    Optional<Vehicule> findByImmatriculation(String immatriculation);

    // Vérifier si une immatriculation existe
    boolean existsByImmatriculation(String immatriculation);

    // Liste des véhicules d'un conducteur
    List<Vehicule> findByConducteurId(Long conducteurId);
}
