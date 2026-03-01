package com.groupe2.METOA.repository;

import com.groupe2.METOA.Entity.Reservation;
import com.groupe2.METOA.Enum.StatutReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepo extends JpaRepository<Reservation, String> {
    @Query("SELECT r FROM Reservation r WHERE r.trajet.idTrajet = :idTrajet AND r.statut = :statut")
    List<Reservation> findReservationPourCovoiturage(@Param("idTrajet") String idTrajet, @Param("statut") StatutReservation statut);
    @Query("SELECT r FROM Reservation r WHERE r.trajet.idTrajet = :idTrajet")
    List<Reservation>findByTrajetId(String idTrajet);
}