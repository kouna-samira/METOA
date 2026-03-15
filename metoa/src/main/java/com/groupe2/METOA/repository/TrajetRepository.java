package com.groupe2.METOA.repository;

import com.metoa.entity.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface TrajetRepository extends JpaRepository<Trajet, Long> {

    List<Trajet> findByConducteurId(Long conducteurId);
    List<Trajet> findByVilleDepartId(Long villeDepartId);
    List<Trajet> findByVilleArriveeId(Long villeArriveeId);
    List<Trajet> findByDateDepartBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT t FROM Trajet t WHERE t.villeDepart.nom = :villeDepart AND t.villeArrivee.nom = :villeArrivee")
    List<Trajet> findByVilleDepartNomAndVilleArriveeNom(@Param("villeDepart") String villeDepart,
                                                        @Param("villeArrivee") String villeArrivee);

    @Query("SELECT t FROM Trajet t WHERE " +
            "(:villeDepart IS NULL OR t.villeDepart.nom = :villeDepart) AND " +
            "(:villeArrivee IS NULL OR t.villeArrivee.nom = :villeArrivee) AND " +
            "(:dateDepart IS NULL OR t.dateDepart = :dateDepart) AND " +
            "(:maxDistance IS NULL OR (6371 * acos(cos(radians(:lat)) * cos(radians(t.latitudeDepart)) * " +
            "cos(radians(t.longitudeDepart) - radians(:lng)) + sin(radians(:lat)) * sin(radians(t.latitudeDepart)))) <= :maxDistance)")
    List<Trajet> rechercheMulticritere(@Param("villeDepart") String villeDepart,
                                       @Param("villeArrivee") String villeArrivee,
                                       @Param("dateDepart") LocalDateTime dateDepart,
                                       @Param("lat") Double latitude,
                                       @Param("lng") Double longitude,
                                       @Param("maxDistance") Double maxDistance);

    @Query("SELECT t FROM Trajet t WHERE " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(t.latitudeDepart)) * " +
            "cos(radians(t.longitudeDepart) - radians(:lng)) + sin(radians(:lat)) * sin(radians(t.latitudeDepart)))) <= :rayon")
    List<Trajet> findProximite(@Param("lat") Double latitude,
                               @Param("lng") Double longitude,
                               @Param("rayon") Double rayonKm);

    boolean existsByVehiculeId(Long vehiculeId);

    // Vérifier si une ville est utilisée en départ OU en arrivée
    boolean existsByVilleDepartIdOrVilleArriveeId(Long villeDepartId, Long villeArriveeId);
}
