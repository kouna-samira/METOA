package com.groupe2.METOA.Service.Paiement;

import com.groupe2.METOA.Dto.PaiementResDto;
import com.groupe2.METOA.Entity.Paiement;
import com.groupe2.METOA.Entity.Reservation;
import com.groupe2.METOA.Enum.StatutPaiement;
import com.groupe2.METOA.Enum.StatutReservation;
import com.groupe2.METOA.repository.PaiementRepo;
import com.groupe2.METOA.repository.ReservationRepo;
import com.groupe2.METOA.exception.RessourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class PaiementServiceImpl implements PaiementService{
    private final PaiementRepo paiementRepo;
    private final ReservationRepo reservationRepo;

    public PaiementServiceImpl(PaiementRepo paiementRepo, ReservationRepo reservationRepo) {
        this.paiementRepo = paiementRepo;
        this.reservationRepo = reservationRepo;
    }

    @Override
    public void addPaiement(String idReservation, double montant) {
        Reservation reservation = reservationRepo.findById(idReservation)
                .orElseThrow(() -> new RessourceNotFoundException("Reservation introuvable"));

        Paiement paiement = Paiement.builder()
                .reservation(reservation)
                .montant(montant)
                .datePaiement(LocalDate.now())
                .statut(StatutPaiement.VALIDE) // On met VALIDE par défaut, tu peux adapter
                .methode("INCONNU") // Par défaut, à adapter si tu veux
                .build();

        paiementRepo.save(paiement);

        // 🔹 Logique métier : confirmer la réservation si paiement VALIDE
        if (paiement.getStatut() == StatutPaiement.VALIDE) {
            reservation.setStatut(StatutReservation.CONFIRMEE);
            reservationRepo.save(reservation);
        }

    }

    @Override
    public List<PaiementResDto> getAllPaiements() {
        List<Paiement> paiements = paiementRepo.findAll();
        List<PaiementResDto> result = new ArrayList<>();

        for (Paiement paiement : paiements) {
            PaiementResDto dto = PaiementResDto.builder()
                    .idPaiement(paiement.getIdPaiement())
                    .datePaiement(paiement.getDatePaiement())
                    .montant(paiement.getMontant())
                    .statut(String.valueOf(paiement.getStatut()))
                    .methode(paiement.getMethode())
                    .idReservation(paiement.getReservation().getIdReservation())
                    .build();
            result.add(dto);
        }
        return result;
    }

    @Override
    public Page<PaiementResDto> getPaiements(int page, int size) {
        Page<Paiement> paiements = paiementRepo.findAll(PageRequest.of(page, size));

        return paiements.map(p -> {
            PaiementResDto dto = new PaiementResDto();
            dto.setIdPaiement(p.getIdPaiement());
            dto.setMontant(p.getMontant());
            dto.setDatePaiement(p.getDatePaiement());
            dto.setIdReservation(p.getReservation() != null ? p.getReservation().getIdReservation() : null);
            return dto;
        });
    }

}
