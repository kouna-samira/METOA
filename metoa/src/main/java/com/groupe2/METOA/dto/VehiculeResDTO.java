package com.groupe2.METOA.dto;

import lombok.Data;

@Data
public class VehiculeResDTO {
    private Long id;
    private String marque;
    private String modele;
    private String couleur;
    private String immatriculation;
    private int nombrePlaces;
    private Long conducteurId;
}
