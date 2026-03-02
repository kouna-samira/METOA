package com.groupe2.METOA.Service.Paiement;

import com.groupe2.METOA.Dto.ClientResDto;
import com.groupe2.METOA.Dto.PaiementResDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PaiementService {
    void addPaiement(String idReservation, double montant);
    List<PaiementResDto> getAllPaiements();
    Page<PaiementResDto> getPaiements(int page, int size);

}
