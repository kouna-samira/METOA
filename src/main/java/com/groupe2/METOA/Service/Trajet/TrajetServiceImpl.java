package com.groupe2.METOA.Service.Trajet;

import com.groupe2.METOA.Dto.TrajetReqDto;
import com.groupe2.METOA.Dto.TrajetResDto;
import com.groupe2.METOA.Entity.Reservation;
import com.groupe2.METOA.Entity.Trajet;
import com.groupe2.METOA.repository.ReservationRepo;
import com.groupe2.METOA.repository.TrajetRepo;
import com.groupe2.METOA.exception.RessourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrajetServiceImpl implements TrajetService{
    private final com.groupe2.METOA.repository.TrajetRepo trajetRepo;
    private final ReservationRepo reservationRepo;

    public TrajetServiceImpl(com.groupe2.METOA.repository.TrajetRepo trajetRepo, ReservationRepo reservationRepo) {
        this.trajetRepo = trajetRepo;
        this.reservationRepo = reservationRepo;
    }

    @Override
    public void addTrajet(TrajetReqDto trajetReqDto) {

        Trajet trajet = new Trajet();

        trajet.setDateTrajet(trajetReqDto.getDateTrajet());
        trajet.setDepart(trajetReqDto.getDepart());
        trajet.setDestination(trajetReqDto.getDestination());
        trajet.setDistance(trajetReqDto.getDistance());

        trajetRepo.save(trajet);
    }

    @Override
    public TrajetResDto getTrajetById(String idTrajet) {
        Trajet trajet = trajetRepo.findById(idTrajet)
                .orElseThrow(() ->
                        new RessourceNotFoundException("Trajet avec id " + idTrajet + " introuvable"));

        return new TrajetResDto(
                trajet.getIdTrajet(),
                trajet.getDateTrajet(),
                trajet.getDepart(),
                trajet.getDestination(),
                trajet.getDistance(),
                trajet.getPrix()
        );
    }

    @Override
    public List<TrajetResDto> getAllTrajets() {
        List<Trajet> trajets = trajetRepo.findAll();
        List<TrajetResDto> result = new ArrayList<>();

        for (Trajet trajet : trajets) {

            TrajetResDto dto = new TrajetResDto(
                    trajet.getIdTrajet(),
                    trajet.getDateTrajet(),
                    trajet.getDepart(),
                    trajet.getDestination(),
                    trajet.getDistance(),
                    trajet.getPrix()
            );
            result.add(dto);
        }
        return result;
    }

    @Override
    public void updateTrajet(String idTrajet, TrajetResDto trajetResDto) {
        Trajet trajet = trajetRepo.findById(idTrajet)
                .orElseThrow(() ->
                        new RessourceNotFoundException("Trajet introuvable"));

        trajet.setDateTrajet(trajetResDto.getDateTrajet());
        trajet.setDepart(trajetResDto.getDepart());
        trajet.setDestination(trajetResDto.getDestination());
        trajet.setDistance(trajetResDto.getDistance());

        trajetRepo.save(trajet);

    }

    @Override
    public void deleteTrajet(String idTrajet) {
        Trajet trajet = trajetRepo.findById(idTrajet)
                .orElseThrow(() -> new RuntimeException("Trajet non trouvé"));

        // CORRECTION: utiliser l'instance injectée, pas la classe
        List<Reservation> reservations = reservationRepo.findByTrajetId(idTrajet);

        // Ajoutez votre logique ici (ex: annuler les réservations)

        trajetRepo.delete(trajet);

    }

    @Override
    public Page<TrajetResDto> getTrajets(int page, int size) {
        Page<Trajet> trajets = trajetRepo.findAll(PageRequest.of(page, size));

        return trajets.map(trajet -> {
            TrajetResDto dto = new TrajetResDto();
            dto.setIdTrajet(trajet.getIdTrajet());
            dto.setDateTrajet(trajet.getDateTrajet());
            dto.setDepart(trajet.getDepart());
            dto.setDestination(trajet.getDestination());
            dto.setDistance(trajet.getDistance());
            dto.setPrix(trajet.getPrix());
            return dto;
        });
    }

}
