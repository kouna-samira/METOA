package com.groupe2.METOA.gestionProfilUtiisateur.repository;



import com.groupe2.METOA.gestionProfilUtiisateur.entity.fonctionaleterAvances.note_avis.entity.Badge;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.ProfileConducteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileConducteurRepo extends JpaRepository<ProfileConducteur, String> {
    Optional<ProfileConducteur> findByUserIdUser(String userId);

    boolean existsByUserIdUser(String userId);

    List<ProfileConducteur> findByBadge(Badge badge);

}
