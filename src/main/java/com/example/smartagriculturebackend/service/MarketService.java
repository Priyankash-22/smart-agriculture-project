package com.example.smartagriculturebackend.service;

import com.example.smartagriculturebackend.entity.Market;
import com.example.smartagriculturebackend.repository.MarketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketService {

    private final MarketRepository marketRepository;

    public MarketService(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    public Market saveMarket(Market market) {
        return marketRepository.save(market);
    }

    public List<Market> getAllMarkets() {
        return marketRepository.findAll();
    }

    public List<Market> getMarketsByLocation(String location) {
        return marketRepository.findByLocationContainingIgnoreCase(location);
    }
}