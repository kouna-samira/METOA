package com.metoa.service;

import com.metoa.entity.Reservation;
import java.util.List;
import java.util.Optional;

public interface ReservationService {
    Reservation creerReservation(Reservation reservation);
    void annulerReservation(Long reservationId);
    Optional<Reservation> getReservation(Long reservationId);
    List<Reservation> getAllReservations();
}
