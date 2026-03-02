package com.metoa.repository;

import com.metoa.entity.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrajetRepository extends JpaRepository<Trajet, Long> {

    // Trajets d'un conducteur
    List<Trajet> findByConducteurId(Long conducteurId);

    // Trajets d'une ville de départ
    List<Trajet> findByVilleDepartId(Long villeDepartId);

    // Trajets d'une ville d'arrivée
    List<Trajet> findByVilleArriveeId(Long villeArriveeId);

    // Trajets entre deux dates (pour recherche par période)
    List<Trajet> findByDateDepartBetween(LocalDateTime start, LocalDateTime end);

    // Recherche par noms de villes (insensible à la casse)
    @Query("SELECT t FROM Trajet t WHERE LOWER(t.villeDepart.nom) = LOWER(:depart) AND LOWER(t.villeArrivee.nom) = LOWER(:arrivee)")
    List<Trajet> findByVilleDepartNomAndVilleArriveeNom(@Param("depart") String depart, @Param("arrivee") String arrivee);

    // Recherche multicritère avec distance maximale (formule de Haversine)
    @Query("SELECT t FROM Trajet t WHERE " +
            "(:villeDepart IS NULL OR LOWER(t.villeDepart.nom) = LOWER(:villeDepart)) AND " +
            "(:villeArrivee IS NULL OR LOWER(t.villeArrivee.nom) = LOWER(:villeArrivee)) AND " +
            "(:dateDepart IS NULL OR t.dateDepart = :dateDepart) AND " +
            "(:maxDistance IS NULL OR (6371 * acos(cos(radians(:lat)) * cos(radians(t.latitudeDepart)) * " +
            "cos(radians(t.longitudeDepart) - radians(:lng)) + sin(radians(:lat)) * sin(radians(t.latitudeDepart)))) <= :maxDistance)")
    List<Trajet> rechercheMulticritere(@Param("villeDepart") String villeDepart,
                                       @Param("villeArrivee") String villeArrivee,
                                       @Param("dateDepart") LocalDateTime dateDepart,
                                       @Param("lat") Double latitude,
                                       @Param("lng") Double longitude,
                                       @Param("maxDistance") Double maxDistance);

    // Recherche par proximité (rayon en km)
    @Query("SELECT t FROM Trajet t WHERE " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(t.latitudeDepart)) * " +
            "cos(radians(t.longitudeDepart) - radians(:lng)) + sin(radians(:lat)) * sin(radians(t.latitudeDepart)))) <= :rayon")
    List<Trajet> findProximite(@Param("lat") Double latitude,
                               @Param("lng") Double longitude,
                               @Param("rayon") Double rayonKm);
}