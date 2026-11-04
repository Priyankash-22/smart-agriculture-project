package com.example.smartagriculturebackend.controller;

import com.example.smartagriculturebackend.entity.Market;
import com.example.smartagriculturebackend.service.MarketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/markets")
@CrossOrigin(origins = "http://localhost:5173")
public class MarketController {

    private final MarketService marketService;

    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    @PostMapping
    public Market addMarket(@RequestBody Market market) {
        return marketService.saveMarket(market);
    }

    @GetMapping
    public List<Market> getAllMarkets() {
        return marketService.getAllMarkets();
    }

    @GetMapping("/location")
    public List<Market> getMarketsByLocation(@RequestParam String location) {
        return marketService.getMarketsByLocation(location);
    }
}