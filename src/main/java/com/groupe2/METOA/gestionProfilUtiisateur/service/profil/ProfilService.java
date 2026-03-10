package com.groupe2.METOA.gestionProfilUtiisateur.service.profil;


public interface ProfilService {
    ProfilResDTO getProfilByUserId(String userId);
    void updateProfilByUserId(String userId, ProfilReqDTO profilReqDTO);
    void deleteProfilByUserId(String userId);
}