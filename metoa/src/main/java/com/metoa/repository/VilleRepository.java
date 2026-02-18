package com.metoa.repository;

import com.metoa.entity.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

//Repository Ville

public interface VilleRepository extends JpaRepository<Ville, Long> {

    // Recherche exacte
    Optional<Ville> findByNomIgnoreCase(String nom);

    // Vérifier existence
    boolean existsByNomIgnoreCase(String nom);

    // Recherche partielle
    List<Ville> findByNomContainingIgnoreCase(String nom);
}
