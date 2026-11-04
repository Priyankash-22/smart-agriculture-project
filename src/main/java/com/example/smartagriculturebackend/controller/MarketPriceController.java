package com.example.smartagriculturebackend.controller;

import com.example.smartagriculturebackend.entity.MarketPrice;
import com.example.smartagriculturebackend.service.MarketPriceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/market-prices")
@CrossOrigin(origins = "http://localhost:5173")
public class MarketPriceController {

    private final MarketPriceService marketPriceService;

    public MarketPriceController(MarketPriceService marketPriceService) {
        this.marketPriceService = marketPriceService;
    }

    @PostMapping
    public MarketPrice addMarketPrice(@RequestBody MarketPrice marketPrice) {
        return marketPriceService.saveMarketPrice(marketPrice);
    }

    @GetMapping
    public List<MarketPrice> getAllMarketPrices() {
        return marketPriceService.getAllMarketPrices();
    }

    @GetMapping("/crop/{cropId}")
    public List<MarketPrice> getPricesByCrop(@PathVariable Long cropId) {
        return marketPriceService.getPricesByCrop(cropId);
    }

    @GetMapping("/market/{marketId}")
    public List<MarketPrice> getPricesByMarket(@PathVariable Long marketId) {
        return marketPriceService.getPricesByMarket(marketId);
    }
}
