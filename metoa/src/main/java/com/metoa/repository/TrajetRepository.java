package com.metoa.repository;

import com.metoa.entity.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//Ce repository accède directement aux trajets en base
public interface TrajetRepository extends JpaRepository<Trajet, Long>{

    //Trouver le trajet par ville de départ
    List<Trajet> findByVilleDepartId(Long VilleDepartId);

    //Trouver le trajet par ville d'arrivée
    List<Trajet> findByVilleArriveeId(Long VilleArriveeId);

    //Trouver le trajet par date
    List<Trajet> findByDateDepart(LocalDateTime dateDepart);

    //Trouver le trajet d'un conducteur
    List<Trajet> findByConducteurId(Long conducteurId);

    List<Trajet> findByVilleDepart_NomIgnoreCaseAndVilleArrivee_NomIgnoreCase(String depart, String arrivee);

    List<Trajet> findByVilleDepartAndVilleArriveeAndDateDepart(String villeDepart, String villeArrivee, String dateDepart);

    /*List<Trajet> findTrajetsMulticritere(String villeDepart, String villeArrivee, String dateDepart, Double maxDistance);

    List<Trajet> findTrajetsProximite(Double latitude, Double longitude, Double rayonKm);
*/
    // ------------------- AJOUTS -------------------

    // Recherche multicritère avec coordonnées GPS et distance max
    @Query("SELECT t FROM Trajet t " +
            "WHERE (:villeDepart IS NULL OR t.villeDepart.nom = :villeDepart) " +
            "AND (:villeArrivee IS NULL OR t.villeArrivee.nom = :villeArrivee) " +
            "AND (:dateDepart IS NULL OR t.dateDepart = :dateDepart) " +
            "AND (:maxDistance IS NULL OR " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(t.latitudeDepart)) * " +
            "cos(radians(t.longitudeDepart) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(t.latitudeDepart)))) <= :maxDistance)")
    List<Trajet> findTrajetsMulticritereGPS(
            @Param("villeDepart") String villeDepart,
            @Param("villeArrivee") String villeArrivee,
            @Param("dateDepart") LocalDate dateDepart,
            @Param("maxDistance") Double maxDistance,
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude);

    // Recherche des trajets autour d'un point dans un rayon en km
    @Query("SELECT t FROM Trajet t " +
            "WHERE (6371 * acos(cos(radians(:latitude)) * cos(radians(t.latitudeDepart)) * " +
            "cos(radians(t.longitudeDepart) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(t.latitudeDepart)))) <= :rayonKm")
    List<Trajet> findTrajetsProximiteGPS(
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("rayonKm") Double rayonKm);

    // Recherche multicritère avec noms de villes
    @Query("SELECT t FROM Trajet t " +
            "WHERE (:villeDepart IS NULL OR t.villeDepart.nom = :villeDepart) " +
            "AND (:villeArrivee IS NULL OR t.villeArrivee.nom = :villeArrivee) " +
            "AND (:dateDepart IS NULL OR t.dateDepart = :dateDepart) " +
            "AND (:maxDistance IS NULL OR " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(t.latitudeDepart)) * " +
            "cos(radians(t.longitudeDepart) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(t.latitudeDepart)))) <= :maxDistance)")
    List<Trajet> findTrajetsMulticritere(
            @Param("villeDepart") String villeDepart,
            @Param("villeArrivee") String villeArrivee,
            @Param("dateDepart") java.time.LocalDateTime dateDepart,
            @Param("maxDistance") Double maxDistance,
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude);

    // Recherche des trajets autour d'un point dans un rayon en km
    @Query("SELECT t FROM Trajet t " +
            "WHERE (6371 * acos(cos(radians(:latitude)) * cos(radians(t.latitudeDepart)) * " +
            "cos(radians(t.longitudeDepart) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(t.latitudeDepart)))) <= :rayonKm)")
    List<Trajet> findTrajetsProximite(
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("rayonKm") Double rayonKm);

    List<Trajet> findTrajetsMulticritere(String villeDepart, String villeArrivee, String dateDepart, Double maxDistance);
}

