package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.service;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.dto.AvisReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.dto.AvisResDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AvisService {

    AvisResDTO createAvis(AvisReqDTO dto);

    Page<AvisResDTO> getAvisByNote(String userId, int note, Pageable pageable);


        Page<AvisResDTO> getAvisUser(String userId, Pageable pageable);

    double calculerNoteMoyenne(String userId);

    void deleteAvis(String avisId);Page<AvisResDTO> getAvisUser(String userId, int page, int size, String sortBy, String direction);
}
