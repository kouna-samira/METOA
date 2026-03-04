package com.metoa.controller;

import com.metoa.dto.GeocodingResultDTO;
import com.metoa.service.GeocodingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/geocode")
@Tag(name = "Géolocalisation", description = "Conversion adresse ↔ coordonnées GPS")
public class GeocodingController {

    private final GeocodingService geocodingService;

    public GeocodingController(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    @GetMapping("/forward")
    @Operation(summary = "Obtenir les coordonnées GPS à partir d'une adresse")
    public GeocodingResultDTO geocode(@RequestParam String address) {
        return geocodingService.geocodeAddress(address);
    }

    @GetMapping("/reverse")
    @Operation(summary = "Obtenir une adresse à partir de coordonnées GPS")
    public String reverseGeocode(@RequestParam double lat, @RequestParam double lon) {
        return geocodingService.reverseGeocode(lat, lon);
    }
}