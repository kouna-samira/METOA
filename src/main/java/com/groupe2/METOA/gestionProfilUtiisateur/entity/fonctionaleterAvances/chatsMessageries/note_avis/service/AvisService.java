package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.note_avis.service;

import com.groupe2.METOA.gestionProfileUtilisateur.entity.note_avis.dto.AvisReqDTO;
import com.groupe2.METOA.gestionProfileUtilisateur.entity.note_avis.dto.AvisResDTO;
import com.groupe2.METOA.gestionProfileUtilisateur.entity.note_avis.dto.StatistiqueAvisDTO;
import com.groupe2.METOA.gestionProfileUtilisateur.entity.note_avis.entity.Badge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AvisService {

    AvisResDTO donnerAvis(String auteurId, AvisReqDTO dto);

    AvisResDTO modifierAvis(String avisId, double note, String commentaire);

    Page<AvisResDTO> getAvisUtilisateur(String userId, Pageable pageable);

    StatistiqueAvisDTO getStatistiques(String userId);

   void updateNoteEtBadge(String userId, Double moyenne, Integer totalAvis, String badge);
}
