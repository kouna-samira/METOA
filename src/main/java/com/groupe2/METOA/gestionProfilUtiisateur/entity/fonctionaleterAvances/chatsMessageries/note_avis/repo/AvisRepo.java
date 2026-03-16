package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.note_avis.repo;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.chatsMessageries.note_avis.entity.Avis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
public interface AvisRepo extends JpaRepository<Avis,String> {

    List<Avis> findByCibleIdUserAndVisibleTrue(String userId);

    Page<Avis> findByCibleIdUser(String userId, Pageable pageable);

    @Query("""
SELECT a FROM Avis a
WHERE a.auteur.idUser = :auteurId
AND a.trajet.idTrajet = :trajetId
""")
    Optional<Avis> findAvisByAuteurAndTrajet(String auteurId, String trajetId);
}