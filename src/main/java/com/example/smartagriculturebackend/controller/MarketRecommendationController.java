
package com.example.smartagriculturebackend.controller;

import com.example.smartagriculturebackend.dto.MarketRecommendationDTO;
import com.example.smartagriculturebackend.service.MarketRecommendationService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/market-recommendations")
@CrossOrigin(origins = "*")
public class MarketRecommendationController {

    private final MarketRecommendationService marketRecommendationService;

    public MarketRecommendationController(
            MarketRecommendationService marketRecommendationService) {
        this.marketRecommendationService = marketRecommendationService;
    }

    @GetMapping("/compare/{farmerId}")
    public List<MarketRecommendationDTO> compareMarkets(
            @PathVariable Long farmerId,
            @RequestParam String cropName,
            @RequestParam Double quantity) {

        return marketRecommendationService.compareMarkets(
                farmerId, cropName, quantity);
    }
}
