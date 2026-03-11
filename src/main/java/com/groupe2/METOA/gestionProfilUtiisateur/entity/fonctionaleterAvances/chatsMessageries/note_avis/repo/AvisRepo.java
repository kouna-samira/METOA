package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.note_avis.repo;

import com.groupe2.METOA.gestionProfileUtilisateur.entity.note_avis.entity.Avis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AvisRepo extends JpaRepository<Avis, String> {

    List<Avis> findByCibleId(String cibleId);

    Optional<Avis> findByAuteurIdAndTrajetId(String auteurId, String trajetId);

    Page<Avis> findByCibleId(String cibleId, Pageable pageable);
}