package com.metoa.repository;

import com.metoa.entity.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VilleRepository extends JpaRepository<Ville, Long> {

    // Recherche par nom exact (insensible à la casse)
    Optional<Ville> findByNomIgnoreCase(String nom);

    // Vérifier si une ville existe
    boolean existsByNomIgnoreCase(String nom);

    // Recherche partielle
    List<Ville> findByNomContainingIgnoreCase(String nom);
}