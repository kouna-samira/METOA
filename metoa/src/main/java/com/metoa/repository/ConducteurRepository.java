package com.metoa.repository;

import com.metoa.entity.Conducteur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

//Repository JPA pour entité Conducteur Fournit automatiquement CRUD + méthodes personnalisées

public interface ConducteurRepository extends JpaRepository<Conducteur, Long> {

    // Trouver conducteur par email (login / identification)
    Optional<Conducteur> findByEmail(String email);

    // Vérifier unicité email
    boolean existsByEmail(String email);

    // Recherche exacte nom + prénom (insensible casse)
    Optional<Conducteur> findByNomIgnoreCaseAndPrenomIgnoreCase(String nom, String prenom);

    // Recherche par nom partiel
    List<Conducteur> findByNomContainingIgnoreCase(String nom);

    // Recherche nom + prénom partiels
    List<Conducteur> findByNomContainingIgnoreCaseAndPrenomContainingIgnoreCase(String nom, String prenom);

    Optional<Conducteur> findByNomAndPrenomAllIgnoreCase(String nom, String prenom);
}
