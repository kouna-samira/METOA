package com.groupe2.METOA.repository;

import com.groupe2.METOA.Entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementRepo extends JpaRepository<Paiement, String> {
}
