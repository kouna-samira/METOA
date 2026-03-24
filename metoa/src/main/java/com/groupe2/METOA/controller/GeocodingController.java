package com.groupe2.METOA.controller;


import com.metoa.dto.GeocodingResultDTO;
import com.metoa.service.GeocodingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<GeocodingResultDTO> geocode(@RequestParam String address) {
        GeocodingResultDTO result = geocodingService.geocodeAddress(address);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/reverse")
    @Operation(summary = "Obtenir une adresse à partir de coordonnées GPS")
    public ResponseEntity<String> reverseGeocode(@RequestParam double lat, @RequestParam double lon) {
        String address = geocodingService.reverseGeocode(lat, lon);
        if (address == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(address);
    }
}