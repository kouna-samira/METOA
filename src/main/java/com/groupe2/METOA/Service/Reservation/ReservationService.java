package com.groupe2.METOA.Service.Reservation;

import com.groupe2.METOA.Dto.ReservationResDto;
import com.groupe2.METOA.Dto.ReservationSuggestionDto;
import com.groupe2.METOA.Entity.Reservation;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReservationService {
    void addReservation(String idClient, String idTrajet);
    ReservationResDto getReservationById(String idReservation);
    List<ReservationResDto> getAllReservations();
    void deleteReservation(String idReservation);
    Page<ReservationResDto> getReservations(int page, int size);
    //fonctionnalite avancee
     ReservationSuggestionDto annulerEtSuggere(String idReservation);
     List<Reservation>getReservationsPourCovoiturage(String idTrajet);
}
