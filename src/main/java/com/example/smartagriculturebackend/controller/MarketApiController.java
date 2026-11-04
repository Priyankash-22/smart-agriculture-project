
package com.example.smartagriculturebackend.controller;

import com.example.smartagriculturebackend.service.MarketApiService;
import com.example.smartagriculturebackend.service.MarketApiImportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/market-api")
public class MarketApiController {

    private final MarketApiService marketApiService;
    private final MarketApiImportService marketApiImportService;

    public MarketApiController(
            MarketApiService marketApiService,
            MarketApiImportService marketApiImportService) {

        this.marketApiService = marketApiService;
        this.marketApiImportService = marketApiImportService;
    }

    @GetMapping("/fetch-test")
    public int testPagination() {
        return marketApiService.fetchLatestDailyRecords().size();
    }

    @GetMapping("/import")
    public String importMarketPrices(
            @RequestParam(defaultValue = "3") int maxPages) {

        return marketApiImportService.importMarketPrices(maxPages);
    }
}
