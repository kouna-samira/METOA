package com.groupe2.METOA.gestionProfilUtiisateur.repository;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
}
