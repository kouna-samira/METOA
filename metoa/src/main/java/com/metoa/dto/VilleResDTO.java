package com.metoa.dto;

import lombok.Data;

@Data
public class VilleResDTO {
    private Long id;
    private String nom;
    private String region;
    private double latitude;
    private double longitude;
}