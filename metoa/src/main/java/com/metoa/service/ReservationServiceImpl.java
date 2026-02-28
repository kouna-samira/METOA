package com.metoa.service;

import com.metoa.entity.Reservation;
import com.metoa.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation creerReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void annulerReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    @Override
    public Optional<Reservation> getReservation(Long reservationId) {
        return reservationRepository.findById(reservationId);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
}
