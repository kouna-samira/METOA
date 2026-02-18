package com.metoa.repository;

import com.metoa.entity.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

//Ce repository accède directement aux trajets en base
public interface TrajetRepository extends JpaRepository<Trajet, Long>{

    //Trouver le trajet par ville de départ
    List<Trajet> findByVilleDepartId(Long VilleDepartId);

    //Trouver le trajet par ville d'arrivée
    List<Trajet> findByVilleArriveetId(Long VilleArriveeId);

    //Trouver le trajet par date
    List<Trajet> findByDateDepart(LocalDate dateDepart);

    //Trouver le trajet d'un conducteur
    List<Trajet> findByConducteurId(Long conducteurId);

}
