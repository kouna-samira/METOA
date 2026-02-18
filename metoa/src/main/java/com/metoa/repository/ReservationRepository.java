package com.metoa.repository;

import com.metoa.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


//Repository Reservation

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Réservations d’un passager
    List<Reservation> findByPassagerId(Long passagerId);

    // Réservations d’un trajet
    List<Reservation> findByTrajetId(Long trajetId);

    // Vérifier si passager déjà réservé
    boolean existsByPassagerIdAndTrajetId(Long passagerId, Long trajetId);
}
