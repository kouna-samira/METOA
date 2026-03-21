package com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.repo;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.entity.Avis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
public interface AvisRepo extends JpaRepository<Avis,String> {

    // Vérifier si un avis existe déjà pour un trajet
    Optional<Avis> findByAuteurIdUserAndTrajetIdTrajet(String auteurId, String trajetId);

    // Tous les avis visibles d’un utilisateur (pagination)
    Page<Avis> findByCibleIdUserAndVisibleTrue(String userId, Pageable pageable);

    // Liste simple (utile pour calcul)
    List<Avis> findByCibleIdUserAndVisibleTrue(String userId);

    // Filtrer par note
    Page<Avis> findByCibleIdUserAndNoteGreaterThanEqualAndVisibleTrue(
            String userId, int note, Pageable pageable);

    // Trier par date
    Page<Avis> findByCibleIdUserAndVisibleTrueOrderByDateAvisDesc(
            String userId, Pageable pageable);}