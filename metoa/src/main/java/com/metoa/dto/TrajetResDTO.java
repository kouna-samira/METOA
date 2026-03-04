/*package com.metoa.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TrajetResDTO {
    private Long id;
    private Long conducteurId;
    private String conducteurNomComplet;
    private Long vehiculeId;
    private String vehiculeImmatriculation;
    private String villeDepart;
    private String villeArrivee;
    private LocalDateTime dateDepart;
    private int placesDisponibles;
    private double prix;
    private String statut;
    private Double latitudeDepart;
    private Double longitudeDepart;
    private Double latitudeArrivee;
    private Double longitudeArrivee;
    private List<Long> reservationsIds;
}*/
package com.metoa.dto;

import com.metoa.entity.StatutTrajet;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TrajetResDTO {

    @Schema(description = "ID du trajet", example = "1")
    private Long id;

    @Schema(description = "ID du conducteur", example = "1")
    private Long conducteurId;

    @Schema(description = "Nom complet du conducteur", example = "Bilong Pradel")
    private String conducteurNomComplet;

    @Schema(description = "ID du véhicule", example = "1")
    private Long vehiculeId;

    @Schema(description = "Immatriculation du véhicule", example = "CE 254 AL")
    private String vehiculeImmatriculation;

    @Schema(description = "Ville de départ", example = "Yaoundé")
    private String villeDepart;

    @Schema(description = "Ville d'arrivée", example = "Douala")
    private String villeArrivee;

    @Schema(description = "Date et heure de départ", example = "2026-03-15T08:00:00")
    private LocalDateTime dateDepart;

    @Schema(description = "Places disponibles", example = "3")
    private int placesDisponibles;

    @Schema(description = "Prix par place (FCFA)", example = "2500")
    private double prix;

    @Schema(description = "Statut du trajet", allowableValues = {"BROUILLON", "PUBLIE", "TERMINE", "ANNULE"}, example = "PUBLIE")
    private String statut;

    @Schema(description = "Latitude du départ", example = "3.8480")
    private Double latitudeDepart;

    @Schema(description = "Longitude du départ", example = "11.5021")
    private Double longitudeDepart;

    @Schema(description = "Latitude de l'arrivée", example = "4.0511")
    private Double latitudeArrivee;

    @Schema(description = "Longitude de l'arrivée", example = "9.7679")
    private Double longitudeArrivee;

    @Schema(description = "Liste des IDs des réservations", example = "[1,2]")
    private List<Long> reservationsIds;
}