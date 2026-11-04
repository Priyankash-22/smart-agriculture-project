
package com.example.smartagriculturebackend.service;

import com.example.smartagriculturebackend.entity.LocationCoordinate;
import tools.jackson.databind.JsonNode;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DistanceService {

    private final GeocodingService geocodingService;
    private final RestTemplate restTemplate;

    public DistanceService(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
        this.restTemplate = new RestTemplate();
    }

    public Double calculateRoadDistance(
            String farmerLocation,
            String marketLocation) {

        LocationCoordinate farmerCoordinates =
                geocodingService.getCoordinates(farmerLocation);

        LocationCoordinate marketCoordinates =
                geocodingService.getCoordinates(marketLocation);

        String coordinates =
                farmerCoordinates.getLongitude() + "," +
                        farmerCoordinates.getLatitude() + ";" +
                        marketCoordinates.getLongitude() + "," +
                        marketCoordinates.getLatitude();

        String url = UriComponentsBuilder
                .fromUriString(
                        "https://router.project-osrm.org/route/v1/driving/"
                                + coordinates)
                .queryParam("overview", "false")
                .build()
                .toUriString();

        JsonNode response = restTemplate.getForObject(
                url, JsonNode.class);

        if (response == null ||
                !response.has("routes") ||
                response.get("routes").isEmpty()) {

            throw new RuntimeException(
                    "Road distance could not be calculated");
        }

        double distanceInMeters =
                response.get("routes")
                        .get(0)
                        .get("distance")
                        .asDouble();

        return Math.round(distanceInMeters / 1000.0 * 10.0) / 10.0;
    }
}

