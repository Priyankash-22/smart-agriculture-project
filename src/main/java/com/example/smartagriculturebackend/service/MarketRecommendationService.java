
package com.example.smartagriculturebackend.service;

import com.example.smartagriculturebackend.dto.MarketRecommendationDTO;
import com.example.smartagriculturebackend.entity.Crop;
import com.example.smartagriculturebackend.entity.Farmer;
import com.example.smartagriculturebackend.entity.MarketPrice;
import com.example.smartagriculturebackend.repository.CropRepository;
import com.example.smartagriculturebackend.repository.FarmerRepository;
import com.example.smartagriculturebackend.repository.MarketPriceRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MarketRecommendationService {

    private static final Logger logger =
            LoggerFactory.getLogger(MarketRecommendationService.class);

    private final FarmerRepository farmerRepository;
    private final CropRepository cropRepository;
    private final MarketPriceRepository marketPriceRepository;
    private final DistanceService distanceService;

    @Value("${market.transport.rate.per.quintal.km:0.50}")
    private Double transportRate;

    public MarketRecommendationService(
            FarmerRepository farmerRepository,
            CropRepository cropRepository,
            MarketPriceRepository marketPriceRepository,
            DistanceService distanceService) {

        this.farmerRepository = farmerRepository;
        this.cropRepository = cropRepository;
        this.marketPriceRepository = marketPriceRepository;
        this.distanceService = distanceService;
    }

    public List<MarketRecommendationDTO> compareMarkets(
            Long farmerId, String cropName, Double quantity) {

        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() ->
                        new RuntimeException("Farmer not found"));

        Crop crop = cropRepository.findByCropNameIgnoreCase(cropName)
                .orElseThrow(() ->
                        new RuntimeException("Crop not found"));

        if (quantity == null || quantity <= 0) {
            throw new RuntimeException(
                    "Quantity must be greater than zero");
        }

        List<MarketPrice> prices =
                marketPriceRepository.findByCropCropId(crop.getCropId());

        if (prices.isEmpty()) {
            throw new RuntimeException(
                    "No market prices available for this crop");
        }

        // Select the latest available price for each market.
        // If multiple varieties exist on the same date,
        // select the variety with the highest modal price.

        Map<Long, MarketPrice> uniqueMarkets = new LinkedHashMap<>();

        for (MarketPrice price : prices) {

            Long marketId = price.getMarket().getMarketId();

            MarketPrice existing = uniqueMarkets.get(marketId);

            if (existing == null ||
                    price.getDate().isAfter(existing.getDate()) ||
                    (price.getDate().isEqual(existing.getDate()) &&
                            price.getPrice() > existing.getPrice())) {

                uniqueMarkets.put(marketId, price);
            }
        }

        List<MarketRecommendationDTO> recommendations =
                new ArrayList<>();

        // Calculate earnings for every available market.

        for (MarketPrice price : uniqueMarkets.values()) {

            try {

                Double distance = distanceService.calculateRoadDistance(
                        farmer.getLocation(),
                        price.getMarket().getLocation()
                );

                if (distance == null || distance < 0) {
                    continue;
                }

                Double revenue = price.getPrice() * quantity;

                Double transportCost =
                        distance * quantity * transportRate;

                Double netEarnings = revenue - transportCost;

                MarketRecommendationDTO dto =
                        new MarketRecommendationDTO();

                dto.setCropName(crop.getCropName());
                dto.setFarmerLocation(farmer.getLocation());

                dto.setMarketName(price.getMarket().getMarketName());
                dto.setMarketLocation(price.getMarket().getLocation());

                dto.setModalPrice(price.getPrice());
                dto.setPriceDate(price.getDate());
                dto.setDistanceKm(distance);
                dto.setQuantity(quantity);

                dto.setEstimatedRevenue(
                        roundToTwoDecimals(revenue));

                dto.setTransportRate(transportRate);

                dto.setEstimatedTransportCost(
                        roundToTwoDecimals(transportCost));

                dto.setEstimatedNetEarnings(
                        roundToTwoDecimals(netEarnings));

                recommendations.add(dto);

            } catch (Exception e) {

                logger.warn(
                        "Could not calculate distance for market: {}",
                        price.getMarket().getMarketName(),
                        e
                );
            }
        }

        if (recommendations.size() < 3) {
            throw new RuntimeException(
                    "Fewer than 3 markets have valid price and distance data. "
                            + "Available markets: " + recommendations.size()
            );
        }

        // Rank markets by highest estimated net earnings.

        recommendations.sort(
                Comparator.comparingDouble(
                        MarketRecommendationDTO::getEstimatedNetEarnings
                ).reversed()
        );

        // Add recommendation reasons.

        recommendations.get(0).setRecommendationReason(
                "Best market: highest estimated net earnings after "
                        + "transportation cost."
        );

        for (int i = 1; i < recommendations.size(); i++) {

            recommendations.get(i).setRecommendationReason(
                    "Alternative market. Estimated net earnings: Rs. "
                            + recommendations.get(i)
                            .getEstimatedNetEarnings()
            );
        }

        // Return the top 3 markets.

        return new ArrayList<>(
                recommendations.subList(0, 3)
        );
    }

    private Double roundToTwoDecimals(Double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
