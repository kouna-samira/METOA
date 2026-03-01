package com.groupe2.METOA.gestionProfilUtiisateur.repository;


import com.groupe2.METOA.gestionProfilUtiisateur.entity.profil.Profil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilRepo extends JpaRepository<Profil, String>  {
}
