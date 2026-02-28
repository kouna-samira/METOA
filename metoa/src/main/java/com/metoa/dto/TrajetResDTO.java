package com.metoa.dto;

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
}