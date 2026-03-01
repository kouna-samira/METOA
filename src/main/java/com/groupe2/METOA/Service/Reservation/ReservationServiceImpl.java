package com.groupe2.METOA.Service.Reservation;

import com.groupe2.METOA.Dto.ReservationResDto;
import com.groupe2.METOA.Dto.ReservationSuggestionDto;
import com.groupe2.METOA.Dto.TrajetSuggestionDto;
import com.groupe2.METOA.Entity.Client;
import com.groupe2.METOA.Entity.Reservation;
import com.groupe2.METOA.Entity.Trajet;
import com.groupe2.METOA.Enum.StatutReservation;
import com.groupe2.METOA.repository.ClientRepo;
import com.groupe2.METOA.repository.ReservationRepo;
import com.groupe2.METOA.repository.TrajetRepo;
import com.groupe2.METOA.exception.RessourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService{
    private final ReservationRepo reservationRepo;
    private final ClientRepo clientRepo;
    private final TrajetRepo trajetRepo;

    public ReservationServiceImpl(ReservationRepo reservationRepo, ClientRepo clientRepo, TrajetRepo trajetRepo) {
        this.reservationRepo = reservationRepo;
        this.clientRepo = clientRepo;
        this.trajetRepo = trajetRepo;
    }

    @Override
    public void addReservation(String idClient, String idTrajet) {clientRepo.findById(idClient).orElseThrow(() -> new RessourceNotFoundException("Client introuvable"));
        //  Vérifier que le client existe
        Client client = clientRepo.findById(idClient)
                .orElseThrow(() -> new RessourceNotFoundException("Client introuvable"));

        //  Vérifier que le trajet existe
        Trajet trajet = trajetRepo.findById(idTrajet)
                .orElseThrow(() -> new RessourceNotFoundException("Trajet introuvable"));

        // Créer la réservation
        Reservation reservation = Reservation.builder()
                .client(client)
                .trajet(trajet)
                .dateReservation(LocalDate.now())
                .nombrePlaces(5)
                .prix(trajet.getPrix())
                .statut(StatutReservation.EN_ATTENTE)
                .build();

        reservationRepo.save(reservation);
    }

    @Override
    public ReservationResDto getReservationById(String idReservation) {
        Reservation reservation = reservationRepo.findById(idReservation)
                .orElseThrow(() -> new RessourceNotFoundException("Reservation introuvable"));

        return ReservationResDto.builder()
                .idReservation(reservation.getIdReservation())
                .dateReservation(reservation.getDateReservation())
                .nombrePlaces(reservation.getNombrePlaces())
                .prix(reservation.getPrix())
                .trajet(reservation.getTrajet())
                .build();
    }

    @Override
    public List<ReservationResDto> getAllReservations() {
        List<Reservation> reservations = reservationRepo.findAll();
        List<ReservationResDto> result = new ArrayList<>();


        for (Reservation reservation : reservations) {

            ReservationResDto dto = ReservationResDto.builder()
                    .idReservation(reservation.getIdReservation())
                    .dateReservation(reservation.getDateReservation())
                    .nombrePlaces(reservation.getNombrePlaces())
                    .prix(reservation.getPrix())
                    .trajet(reservation.getTrajet())
                    .build();

            result.add(dto);
        }
        return result;
    }

    @Override
    public void deleteReservation(String idReservation) {
        if (!reservationRepo.existsById(idReservation)) {
            throw new RessourceNotFoundException("Reservation introuvable");
        }
        reservationRepo.deleteById(idReservation);

    }

    @Override
    public Page<ReservationResDto> getReservations(int page, int size) {
        Page<Reservation> reservations =
                reservationRepo.findAll(PageRequest.of(page, size));

        return reservations.map(reservation -> {
            ReservationResDto dto = new ReservationResDto();
            dto.setIdReservation(reservation.getIdReservation());
            dto.setDateReservation(reservation.getDateReservation());
            return dto;
        });
    }
    //fonctionnalite pour l'annulation intelligente
    @Override
    public ReservationSuggestionDto annulerEtSuggere(String idReservation) {

        // 1. Récupérer la réservation annulée
        Reservation reservation = reservationRepo.findById(idReservation)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        // 2. Récupérer le trajet de la réservation
        Trajet trajetOriginal = reservation.getTrajet();

        // 3.  CHERCHER LES VRAIS TRAJETS EN BASE
        List<Trajet> trajetsAlternatifs = trajetRepo.findByDepartAndDestination(
                trajetOriginal.getDepart(),
                trajetOriginal.getDestination()
        );

        //  Convertir CES TRAJETS en DTO
        List<TrajetSuggestionDto> suggestions = trajetsAlternatifs.stream()
                .map(trajet -> {
                    TrajetSuggestionDto dto = new TrajetSuggestionDto();
                    dto.setIdTrajet(trajet.getIdTrajet());
                    dto.setDateTrajet(trajet.getDateTrajet());
                    dto.setDepart(trajet.getDepart());
                    dto.setDestination(trajet.getDestination());
                    dto.setPrix(trajet.getPrix());
                    // etc.
                    return dto;
                })
                .collect(Collectors.toList());

        // 5. Retourner le DTO avec les suggestions
        ReservationSuggestionDto result = new ReservationSuggestionDto();
        result.setIdReservation(idReservation);
        result.setMessage("Votre réservation a été annulée. Voici des alternatives disponibles.");
        result.setSuggestions(suggestions);

        return result;
    }

    @Override
    public List<Reservation> getReservationsPourCovoiturage(String idTrajet) {
        System.out.println("Recherche pour le trajet: " + idTrajet);

        List<Reservation> reservations = reservationRepo.findReservationPourCovoiturage(
                idTrajet,
                StatutReservation.CONFIRMEE
        );

        // Ajoutez ce print pour voir le nombre trouvé
        System.out.println("Réservations trouvées: " + reservations.size());

        return reservations;
    }

    private ReservationSuggestionDto buildSuggestionDto( Reservation reservation,List<Trajet> suggestions){
        List<TrajetSuggestionDto> trajetDtos = suggestions.stream()
                .map(trajet -> new TrajetSuggestionDto(
                        trajet.getIdTrajet(),
                        trajet.getDateTrajet(),
                        trajet.getDepart(),
                        trajet.getDestination(),
                        trajet.getPrix()
                ))
                .toList();

        return ReservationSuggestionDto.builder()
                .message("Votre réservation a été annulée. Voici des alternatives disponibles.")
                .suggestions(trajetDtos)
                .build();
    }



}
