package com.example.smartagriculturebackend.service;

import com.example.smartagriculturebackend.entity.MarketPrice;
import com.example.smartagriculturebackend.repository.MarketPriceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketPriceService {

    private final MarketPriceRepository marketPriceRepository;

    public MarketPriceService(MarketPriceRepository marketPriceRepository) {
        this.marketPriceRepository = marketPriceRepository;
    }

    public MarketPrice saveMarketPrice(MarketPrice marketPrice) {
        return marketPriceRepository.save(marketPrice);
    }

    public List<MarketPrice> getAllMarketPrices() {
        return marketPriceRepository.findAll();
    }

    public List<MarketPrice> getPricesByCrop(Long cropId) {
        return marketPriceRepository.findByCropCropId(cropId);
    }

    public List<MarketPrice> getPricesByMarket(Long marketId) {
        return marketPriceRepository.findByMarketMarketId(marketId);
    }
}