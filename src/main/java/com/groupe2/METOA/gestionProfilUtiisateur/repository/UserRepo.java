package com.groupe2.METOA.gestionProfilUtiisateur.repository;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.Role;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.StatusUser;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserName(String userName);

    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);

    List<User> findByNomIgnoreCase(String nom);

    List<User> findByPrenomIgnoreCase(String prenom);

    Optional<User> findByEmailIgnoreCase(String email);

    List<User> findByVille(String ville);

    List<User> findByRole(Role role);

    List<User> findByStatusUser(StatusUser status);
}
