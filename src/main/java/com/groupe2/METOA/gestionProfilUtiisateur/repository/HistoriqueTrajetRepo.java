package com.groupe2.METOA.gestionProfilUtiisateur.repository;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.historiqueTrajet.HistoriqueTrajet;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.historiqueTrajet.StatusTrajet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoriqueTrajetRepo extends JpaRepository<HistoriqueTrajet, String> {

    List<HistoriqueTrajet> findByUser_IdUser(String idUser);
    List<HistoriqueTrajet> findByUser_IdUserAndStatusTrajet(String idUser, StatusTrajet statusTrajet);

}
