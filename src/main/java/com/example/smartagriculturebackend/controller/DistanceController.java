
package com.example.smartagriculturebackend.controller;

import com.example.smartagriculturebackend.service.DistanceService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/distance")
@CrossOrigin(origins = "*")
public class DistanceController {

    private final DistanceService distanceService;

    public DistanceController(DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    @GetMapping("/calculate")
    public Map<String, Object> calculateDistance(
            @RequestParam String farmerLocation,
            @RequestParam String marketLocation) {

        Double distance = distanceService.calculateRoadDistance(
                farmerLocation, marketLocation);

        return Map.of(
                "farmerLocation", farmerLocation,
                "marketLocation", marketLocation,
                "distanceKm", distance
        );
    }
}
