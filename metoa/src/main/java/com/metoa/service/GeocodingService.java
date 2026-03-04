//package com.metoa.service;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.metoa.dto.GeocodingResultDTO;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.text.Normalizer;
//
//@Service
//public class GeocodingService {
//
//    private static final Logger logger = LoggerFactory.getLogger(GeocodingService.class);
//    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/search";
//    private final RestTemplate restTemplate;
//    private final ObjectMapper objectMapper;
//
//    public GeocodingService() {
//        this.restTemplate = new RestTemplate();
//        this.objectMapper = new ObjectMapper();
//    }
//
//    /**
//     * Convertit une adresse en coordonnées GPS.
//     */
//    public GeocodingResultDTO geocodeAddress(String address) {
//        try {
//            // Normalisation de l'adresse (suppression des accents)
//            String normalizedAddress = normalizeAddress(address);
//            String url = UriComponentsBuilder.fromUriString(NOMINATIM_URL)
//                    .queryParam("q", normalizedAddress + ", Cameroun")
//                    .queryParam("format", "json")
//                    .queryParam("limit", 1)
//                    .toUriString();
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("User-Agent", "METOA-Covoiturage/1.0 (contact@metoa.com)");
//            HttpEntity<?> entity = new HttpEntity<>(headers);
//
//            logger.debug("Appel à Nominatim: {}", url);
//            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//
//            // Vérifier le code HTTP
//            if (!response.getStatusCode().is2xxSuccessful()) {
//                logger.error("Erreur HTTP {} : {}", response.getStatusCode(), response.getBody());
//                return null;
//            }
//
//            logger.debug("Réponse brute: {}", response.getBody());
//
//            JsonNode root = objectMapper.readTree(response.getBody());
//            if (root.isArray() && root.size() > 0) {
//                JsonNode firstResult = root.get(0);
//                GeocodingResultDTO result = new GeocodingResultDTO();
//                result.setLatitude(firstResult.get("lat").asDouble());
//                result.setLongitude(firstResult.get("lon").asDouble());
//                result.setFormattedAddress(firstResult.get("display_name").asText());
//                result.setPlaceId(firstResult.get("place_id").asLong());
//                return result;
//            } else {
//                logger.warn("Aucun résultat trouvé pour l'adresse: {} (normalisée: {})", address, normalizedAddress);
//            }
//        } catch (Exception e) {
//            logger.error("Exception lors du géocodage de l'adresse: " + address, e);
//        }
//        return null;
//    }
//
//    /**
//     * Géocodage inverse : coordonnées -> adresse.
//     */
//    public String reverseGeocode(double lat, double lon) {
//        try {
//            String url = UriComponentsBuilder.fromUriString("https://nominatim.openstreetmap.org/reverse")
//                    .queryParam("lat", lat)
//                    .queryParam("lon", lon)
//                    .queryParam("format", "json")
//                    .toUriString();
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("User-Agent", "METOA-Covoiturage/1.0");
//            HttpEntity<?> entity = new HttpEntity<>(headers);
//
//            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
//            if (!response.getStatusCode().is2xxSuccessful()) {
//                logger.error("Erreur HTTP {} : {}", response.getStatusCode(), response.getBody());
//                return null;
//            }
//
//            JsonNode root = objectMapper.readTree(response.getBody());
//            if (root.has("display_name")) {
//                return root.get("display_name").asText();
//            } else {
//                logger.warn("Aucune adresse trouvée pour les coordonnées: {}, {}", lat, lon);
//            }
//        } catch (Exception e) {
//            logger.error("Erreur lors du géocodage inverse", e);
//        }
//        return null;
//    }
//
//    /**
//     * Supprime les accents et autres diacritiques d'une chaîne.
//     */
//    private String normalizeAddress(String address) {
//        String normalized = Normalizer.normalize(address, Normalizer.Form.NFD);
//        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
//    }
//}

package com.metoa.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metoa.dto.GeocodingResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

@Service
public class GeocodingService {

    private static final Logger logger = LoggerFactory.getLogger(GeocodingService.class);
    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/search";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GeocodingService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public GeocodingResultDTO geocodeAddress(String address) {
        String normalized = normalizeAddress(address);

        // Liste des variantes à essayer
        List<String> queries = new ArrayList<>();
        queries.add(normalized + ", Cameroun");
        queries.add(normalized + ", Cameroon");
        queries.add(normalized); // sans pays

        for (String query : queries) {
            GeocodingResultDTO result = tryGeocode(query);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private GeocodingResultDTO tryGeocode(String query) {
        try {
            String url = UriComponentsBuilder.fromUriString(NOMINATIM_URL)
                    .queryParam("q", query)
                    .queryParam("format", "json")
                    .queryParam("limit", 1)
                    .queryParam("countrycodes", "cm") // restreint au Cameroun
                    .toUriString();

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "METOA-Covoiturage/1.0 (contact@metoa.com)");
            HttpEntity<?> entity = new HttpEntity<>(headers);

            logger.debug("Appel à Nominatim: {}", url);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            logger.debug("Statut HTTP: {}", response.getStatusCode());
            logger.debug("Réponse brute: {}", response.getBody());

            if (!response.getStatusCode().is2xxSuccessful()) {
                logger.error("Erreur HTTP {} : {}", response.getStatusCode(), response.getBody());
                return null;
            }

            JsonNode root = objectMapper.readTree(response.getBody());
            if (root.isArray() && root.size() > 0) {
                JsonNode firstResult = root.get(0);
                GeocodingResultDTO result = new GeocodingResultDTO();
                result.setLatitude(firstResult.get("lat").asDouble());
                result.setLongitude(firstResult.get("lon").asDouble());
                result.setFormattedAddress(firstResult.get("display_name").asText());
                result.setPlaceId(firstResult.get("place_id").asLong());
                return result;
            } else {
                logger.warn("Aucun résultat trouvé pour la requête: {}", query);
            }
        } catch (Exception e) {
            logger.error("Exception lors du géocodage pour la requête: " + query, e);
        }
        return null;
    }

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
            if (!response.getStatusCode().is2xxSuccessful()) {
                logger.error("Erreur HTTP {} : {}", response.getStatusCode(), response.getBody());
                return null;
            }

            JsonNode root = objectMapper.readTree(response.getBody());
            if (root.has("display_name")) {
                return root.get("display_name").asText();
            } else {
                logger.warn("Aucune adresse trouvée pour les coordonnées: {}, {}", lat, lon);
            }
        } catch (Exception e) {
            logger.error("Erreur lors du géocodage inverse", e);
        }
        return null;
    }

    private String normalizeAddress(String address) {
        String normalized = Normalizer.normalize(address, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}