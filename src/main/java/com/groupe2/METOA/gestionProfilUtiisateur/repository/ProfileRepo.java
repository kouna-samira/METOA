package com.groupe2.METOA.gestionProfilUtiisateur.repository;


import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepo extends JpaRepository<Profile,String> {

    Optional<Profile> findByUser_IdUser(String idUser);

    void deleteByUser_IdUser(String idUser);

}