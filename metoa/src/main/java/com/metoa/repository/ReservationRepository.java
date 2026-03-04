/*package com.metoa.repository;

import com.metoa.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Réservations d'un passager
    List<Reservation> findByPassagerId(Long passagerId);

    // Réservations pour un trajet
    List<Reservation> findByTrajetId(Long trajetId);

    // Vérifier si un passager a déjà réservé pour un trajet
    boolean existsByPassagerIdAndTrajetId(Long passagerId, Long trajetId);
}*/
package com.metoa.repository;

import com.metoa.entity.Reservation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByPassagerId(Long passagerId);

    List<Reservation> findByTrajetId(Long trajetId);

    boolean existsByPassagerIdAndTrajetId(Long passagerId, Long trajetId);

    @Override
    @EntityGraph(attributePaths = {"trajet", "trajet.villeDepart", "trajet.villeArrivee", "passager"})
    Optional<Reservation> findById(Long id);
}