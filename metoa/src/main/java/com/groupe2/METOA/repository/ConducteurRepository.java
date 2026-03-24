package com.groupe2.METOA.repository;

import com.metoa.entity.Conducteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConducteurRepository extends JpaRepository<Conducteur, Long> {

    // Trouver un conducteur par email (connexion)
    Optional<Conducteur> findByEmail(String email);

    // Vérifier si un email existe déjà
    boolean existsByEmail(String email);

    // Recherche exacte insensible à la casse par nom et prénom
    Optional<Conducteur> findByNomIgnoreCaseAndPrenomIgnoreCase(String nom, String prenom);

    // Recherche par nom partiel (insensible à la casse)
    List<Conducteur> findByNomContainingIgnoreCase(String nom);

    // Recherche par nom et prénom partiels
    List<Conducteur> findByNomContainingIgnoreCaseAndPrenomContainingIgnoreCase(String nom, String prenom);
}
