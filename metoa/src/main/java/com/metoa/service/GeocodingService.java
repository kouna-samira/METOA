package com.metoa.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metoa.dto.GeocodingResultDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;

@Service
public class GeocodingService {

    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/search";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GeocodingService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Convertit une adresse (ville, lieu) en coordonnées GPS
     */
    public GeocodingResultDTO geocodeAddress(String address) {
        try {
            // Construction de l'URL avec UriComponentsBuilder (encodage automatique)
            String url = UriComponentsBuilder.fromUriString(NOMINATIM_URL)
                    .queryParam("q", address + ", Cameroun")
                    .queryParam("format", "json")
                    .queryParam("limit", 1)
                    .toUriString();

            // Configuration des headers (obligatoire pour Nominatim)
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "METOA-Covoiturage/1.0 (contact@metoa.com)");
            HttpEntity<?> entity = new HttpEntity<>(headers);

            // Appel à l'API Nominatim
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            // Traitement de la réponse JSON
            JsonNode root = objectMapper.readTree(response.getBody());
            if (root.isArray() && root.size() > 0) {
                JsonNode firstResult = root.get(0);
                GeocodingResultDTO result = new GeocodingResultDTO();
                result.setLatitude(firstResult.get("lat").asDouble());
                result.setLongitude(firstResult.get("lon").asDouble());
                result.setFormattedAddress(firstResult.get("display_name").asText());
                result.setPlaceId(firstResult.get("place_id").asLong());
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Géocodage inverse : coordonnées → adresse
     */
    public String reverseGeocode(double lat, double lon) {
        try {
            String url = UriComponentsBuilder.fromUriString("https://nominatim.openstreetmap.org/reverse")
                    .queryParam("lat", lat)
                    .queryParam("lon", lon)
                    .queryParam("format", "json")
                    .toUriString();

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "METOA-Covoiturage/1.0");
            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            JsonNode root = objectMapper.readTree(response.getBody());

            if (root.has("display_name")) {
                return root.get("display_name").asText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}