package com.groupe2.METOA.gestionProfilUtiisateur.repository;



import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.ProfileConducteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileConducteurRepo extends JpaRepository<ProfileConducteur, String> {

    Optional<ProfileConducteur> findByUser_IdUser(String idUser);
}
