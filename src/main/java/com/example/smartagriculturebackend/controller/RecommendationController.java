package com.example.smartagriculturebackend.controller;


import com.example.smartagriculturebackend.entity.Crop;
import com.example.smartagriculturebackend.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@CrossOrigin(origins = "http://localhost:5173")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(
            RecommendationService recommendationService) {

        this.recommendationService = recommendationService;
    }

    @GetMapping("/{farmerId}")
    public List<Crop> getRecommendations(
            @PathVariable Long farmerId) {

        return recommendationService.recommendCrops(farmerId);
    }
}

