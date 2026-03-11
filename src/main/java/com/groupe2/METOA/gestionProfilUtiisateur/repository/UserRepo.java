package com.groupe2.METOA.gestionProfilUtiisateur.repository;

import com.groupe2.METOA.gestionProfilUtiisateur.entity.Role;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.StatusUser;
import com.groupe2.METOA.gestionProfilUtiisateur.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> , JpaSpecificationExecutor<User> {

    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);

    Optional<User> findByEmailIgnoreCase(String email);

    List<User> findByNomIgnoreCase(String nom);

    List<User> findByPrenomIgnoreCase(String prenom);

    @Query("""
SELECT u FROM User u
WHERE
LOWER(u.nom) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(u.prenom) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(u.ville) LIKE LOWER(CONCAT('%', :keyword, '%'))
""")
    Page<User> searchUsers(String keyword, Pageable pageable);
}
