package com.example.smartagriculturebackend.controller;

import com.example.smartagriculturebackend.service.MarketCsvImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/market-data")
@CrossOrigin(origins = "http://localhost:5173")
public class MarketCsvImportController {

    private final MarketCsvImportService marketCsvImportService;

    public MarketCsvImportController(
            MarketCsvImportService marketCsvImportService) {
        this.marketCsvImportService = marketCsvImportService;
    }

    @PostMapping("/import-csv")
    public ResponseEntity<?> importMarketPrices() {

        try {
            Map<String, Object> result =
                    marketCsvImportService.importMarketPrices();

            return ResponseEntity.ok(result);

        } catch (Exception e) {

            return ResponseEntity.internalServerError().body(
                    Map.of(
                            "message", "CSV import failed",
                            "error", e.getMessage()
                    )
            );
        }
    }
}