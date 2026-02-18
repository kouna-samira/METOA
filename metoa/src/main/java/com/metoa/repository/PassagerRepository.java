package com.metoa.repository;

import com.metoa.entity.Passager;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

//Repository Passager

public interface PassagerRepository extends JpaRepository<Passager, Long> {

    Optional<Passager> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Passager> findByNomIgnoreCaseAndPrenomIgnoreCase(String nom, String prenom);

    List<Passager> findByNomContainingIgnoreCase(String nom);

    List<Passager> findByNomContainingIgnoreCaseAndPrenomContainingIgnoreCase(String nom, String prenom);

    Optional<Passager> findByNomAndPrenomAllIgnoreCase(String nom, String prenom);
}
