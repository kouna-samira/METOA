package com.groupe2.METOA.Repository;

import com.groupe2.METOA.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client,String> {
}
