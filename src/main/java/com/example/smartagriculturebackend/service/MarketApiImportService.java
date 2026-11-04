
package com.example.smartagriculturebackend.service;

import com.example.smartagriculturebackend.entity.Crop;
import com.example.smartagriculturebackend.entity.Market;
import com.example.smartagriculturebackend.entity.MarketPrice;
import com.example.smartagriculturebackend.repository.CropRepository;
import com.example.smartagriculturebackend.repository.MarketRepository;
import com.example.smartagriculturebackend.repository.MarketPriceRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tools.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class MarketApiImportService {

    private static final Logger logger =
            LoggerFactory.getLogger(MarketApiImportService.class);

    private final MarketApiService marketApiService;
    private final MarketRepository marketRepository;
    private final CropRepository cropRepository;
    private final MarketPriceRepository marketPriceRepository;

    private final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public MarketApiImportService(
            MarketApiService marketApiService,
            MarketRepository marketRepository,
            CropRepository cropRepository,
            MarketPriceRepository marketPriceRepository) {

        this.marketApiService = marketApiService;
        this.marketRepository = marketRepository;
        this.cropRepository = cropRepository;
        this.marketPriceRepository = marketPriceRepository;
    }

    @Transactional
    public String importMarketPrices(int maxPages) {

        List<JsonNode> records =
                marketApiService.fetchLatestDailyRecords();

        int inserted = 0;
        int duplicates = 0;
        int invalid = 0;

        for (JsonNode record : records) {

            try {
                String marketName = record.get("Market").asString();
                String district = record.get("District").asString();
                String cropName = record.get("Commodity").asString();

                String variety = record.get("Variety").asString();
                String grade = record.get("Grade").asString();

                LocalDate date = LocalDate.parse(
                        record.get("Arrival_Date").asString(),
                        dateFormatter
                );

                Double minPrice =
                        record.get("Min_Price").asDouble();

                Double maxPrice =
                        record.get("Max_Price").asDouble();

                Double modalPrice =
                        record.get("Modal_Price").asDouble();

                Optional<Market> existingMarket =
                        marketRepository
                                .findByMarketNameIgnoreCaseAndLocationIgnoreCase(
                                        marketName, district);

                Market market;

                if (existingMarket.isPresent()) {
                    market = existingMarket.get();
                } else {
                    market = new Market();
                    market.setMarketName(marketName);
                    market.setLocation(district);
                    market = marketRepository.save(market);
                }

                Optional<Crop> existingCrop =
                        cropRepository.findByCropNameIgnoreCase(cropName);

                Crop crop;

                if (existingCrop.isPresent()) {
                    crop = existingCrop.get();
                } else {
                    crop = new Crop();
                    crop.setCropName(cropName);
                    crop = cropRepository.save(crop);
                }

                boolean duplicate =
                        marketPriceRepository
                                .existsByMarketMarketIdAndCropCropIdAndDateAndVarietyAndGrade(
                                        market.getMarketId(),
                                        crop.getCropId(),
                                        date,
                                        variety,
                                        grade
                                );

                if (duplicate) {
                    duplicates++;
                    continue;
                }

                MarketPrice marketPrice = new MarketPrice();

                marketPrice.setMarket(market);
                marketPrice.setCrop(crop);
                marketPrice.setDate(date);
                marketPrice.setMinPrice(minPrice);
                marketPrice.setMaxPrice(maxPrice);
                marketPrice.setPrice(modalPrice);
                marketPrice.setVariety(variety);
                marketPrice.setGrade(grade);

                marketPriceRepository.save(marketPrice);

                inserted++;

            } catch (Exception e) {
                invalid++;
                logger.warn("Skipping invalid market price record: {}",
                        e.getMessage());
            }
        }

        String result = "Total records fetched: " + records.size()
                + ", Inserted: " + inserted
                + ", Duplicates skipped: " + duplicates
                + ", Invalid records: " + invalid;

        logger.info(result);

        return result;
    }

    @Scheduled(cron = "0 0 6 * * *", zone = "Asia/Kolkata")
    public void scheduledMarketPriceImport() {

        logger.info("Daily market price import started.");

        try {
            String result = importMarketPrices(3);
            logger.info(result);

        } catch (Exception e) {
            logger.error("Daily market price import failed.", e);
        }

        logger.info("Daily market price import completed.");
    }
}
