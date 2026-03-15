package com.groupe2.METOA.repository;

import com.groupe2.METOA.Entity.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrajetRepo  extends JpaRepository<Trajet, String> {
    @Query("SELECT t FROM Trajet t WHERE t.depart = :depart AND t.destination = :destination AND t.distance > 0")
    List<Trajet> trouverAlternatives(@Param("depart") String depart, @Param("destination") String destination);
    List<Trajet> findByDepartAndDestination(String depart, String destination);
}
