
package com.example.smartagriculturebackend.service;

import tools.jackson.databind.JsonNode;
import com.example.smartagriculturebackend.entity.LocationCoordinate;
import com.example.smartagriculturebackend.repository.LocationCoordinateRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;

;

@Service
public class GeocodingService {

    private final LocationCoordinateRepository repository;
    private final RestTemplate restTemplate;

    public GeocodingService(LocationCoordinateRepository repository) {
        this.repository = repository;
        this.restTemplate = new RestTemplate();
    }

    public LocationCoordinate getCoordinates(String location) {

        return repository.findByLocationNameIgnoreCase(location)
                .orElseGet(() -> fetchAndSaveCoordinates(location));
    }

    private LocationCoordinate fetchAndSaveCoordinates(String location) {

        String searchLocation = location;

        if (!location.toLowerCase().contains("india")) {
            searchLocation = location + ", Karnataka, India";
        }

        String url = UriComponentsBuilder
                .fromUriString("https://nominatim.openstreetmap.org/search")
                .queryParam("q", location)
                .queryParam("countrycodes", "in")
                .queryParam("format", "jsonv2")
                .queryParam("limit", 1)
                .build()
                .encode()
                .toUriString();

        System.out.println("Searching location: " + location);
        System.out.println("Geocoding URL: " + url);

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent",
                "SmartAgriculturePlatform/1.0 (student project)");

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                JsonNode.class
        );

        JsonNode results = response.getBody();
        System.out.println("Nominatim response: " + results);

        if (results == null || results.isNull()) {
            throw new RuntimeException(
                    "Coordinates not found for location: " + location);
        }

        JsonNode firstResult;

        if (results.isArray()) {

            if (results.isEmpty()) {
                throw new RuntimeException(
                        "Coordinates not found for location: " + location);
            }

            firstResult = results.get(0);

        } else if (results.isObject()
                && results.has("lat")
                && results.has("lon")) {

            firstResult = results;

        } else {
            throw new RuntimeException(
                    "Invalid geocoding response for location: " + location);
        }

        LocationCoordinate coordinate = new LocationCoordinate();
        coordinate.setLocationName(location);
        coordinate.setLatitude(firstResult.get("lat").asDouble());
        coordinate.setLongitude(firstResult.get("lon").asDouble());

        return repository.save(coordinate);
    }


}

