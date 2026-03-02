package com.metoa.dto;

import lombok.Data;

@Data
public class GeocodingResultDTO {
    private double latitude;
    private double longitude;
    private String formattedAddress;
    private long placeId;
}