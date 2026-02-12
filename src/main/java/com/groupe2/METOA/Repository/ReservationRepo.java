package com.groupe2.METOA.Repository;

import com.groupe2.METOA.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepo extends JpaRepository<Reservation,String> {
}
