package com.groupe2.METOA.gestionProfilUtiisateur.repository;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.ProfilePassager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfilePassagerRepo extends JpaRepository<ProfilePassager, String> {
    Optional<ProfilePassager> findByUser_IdUser(String userId);
}