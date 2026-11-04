package com.example.smartagriculturebackend.dto;

import java.time.LocalDate;

public class MarketRecommendationDTO {

    private String cropName;
    private String farmerLocation;
    private String marketName;
    private String marketLocation;
    private Double modalPrice;
    private LocalDate priceDate;
    private Double distanceKm;
    private Double quantity;
    private Double estimatedRevenue;
    private Double transportRate;
    private Double estimatedTransportCost;
    private Double estimatedNetEarnings;
    private String recommendationReason;

    public MarketRecommendationDTO() {
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getFarmerLocation() {
        return farmerLocation;
    }

    public void setFarmerLocation(String farmerLocation) {
        this.farmerLocation = farmerLocation;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getMarketLocation() {
        return marketLocation;
    }

    public void setMarketLocation(String marketLocation) {
        this.marketLocation = marketLocation;
    }

    public Double getModalPrice() {
        return modalPrice;
    }

    public void setModalPrice(Double modalPrice) {
        this.modalPrice = modalPrice;
    }

    public LocalDate getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(LocalDate priceDate) {
        this.priceDate = priceDate;
    }

    public Double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(Double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getEstimatedRevenue() {
        return estimatedRevenue;
    }

    public void setEstimatedRevenue(Double estimatedRevenue) {
        this.estimatedRevenue = estimatedRevenue;
    }

    public Double getTransportRate() {
        return transportRate;
    }

    public void setTransportRate(Double transportRate) {
        this.transportRate = transportRate;
    }

    public Double getEstimatedTransportCost() {
        return estimatedTransportCost;
    }

    public void setEstimatedTransportCost(Double estimatedTransportCost) {
        this.estimatedTransportCost = estimatedTransportCost;
    }

    public Double getEstimatedNetEarnings() {
        return estimatedNetEarnings;
    }

    public void setEstimatedNetEarnings(Double estimatedNetEarnings) {
        this.estimatedNetEarnings = estimatedNetEarnings;
    }

    public String getRecommendationReason() {
        return recommendationReason;
    }

    public void setRecommendationReason(String recommendationReason) {
        this.recommendationReason = recommendationReason;
    }
}