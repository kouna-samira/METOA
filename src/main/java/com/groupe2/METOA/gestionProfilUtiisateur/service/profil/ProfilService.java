package com.groupe2.METOA.gestionProfilUtiisateur.service.profil;


import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.ProfilReqDTO;
import com.groupe2.METOA.gestionProfilUtiisateur.dto.profilDTO.ProfilResDTO;

public interface ProfilService {
    ProfilResDTO getProfilByUserId(String userId);
    void updateProfilByUserId(String userId, ProfilReqDTO profilReqDTO);
    void deleteProfilByUserId(String userId);
}