package com.groupe2.METOA.repository;

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
