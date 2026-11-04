package com.example.smartagriculturebackend.service;

import com.example.smartagriculturebackend.entity.Crop;
import com.example.smartagriculturebackend.entity.Market;
import com.example.smartagriculturebackend.entity.MarketPrice;
import com.example.smartagriculturebackend.repository.CropRepository;
import com.example.smartagriculturebackend.repository.MarketPriceRepository;
import com.example.smartagriculturebackend.repository.MarketRepository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class MarketCsvImportService {

    private final MarketRepository marketRepository;
    private final MarketPriceRepository marketPriceRepository;
    private final CropRepository cropRepository;

    @Value("${app.market.csv-path:market-data/daily/market_prices.csv}")
    private String csvPath;

    public MarketCsvImportService(
            MarketRepository marketRepository,
            MarketPriceRepository marketPriceRepository,
            CropRepository cropRepository) {

        this.marketRepository = marketRepository;
        this.marketPriceRepository = marketPriceRepository;
        this.cropRepository = cropRepository;
    }

    @Transactional
    public Map<String, Object> importMarketPrices() throws Exception {

        Path filePath = Path.of(csvPath);

        if (!Files.exists(filePath)) {
            throw new RuntimeException("CSV file not found: " + filePath.toAbsolutePath());
        }

        int recordsRead = 0;
        int inserted = 0;
        int duplicates = 0;
        int skippedOtherStates = 0;
        int invalid = 0;

        try (
                BufferedReader reader = Files.newBufferedReader(
                        filePath, StandardCharsets.UTF_8);

                CSVParser parser = CSVFormat.DEFAULT.builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .setTrim(true)
                        .get()
                        .parse(reader)
        ) {

            for (CSVRecord record : parser) {

                recordsRead++;

                try {
                    String state = record.get("State").trim();

                    if (!state.equalsIgnoreCase("Karnataka")) {
                        skippedOtherStates++;
                        continue;
                    }

                    String district = record.get("District").trim();
                    String marketName = record.get("Market").trim();
                    String cropName = record.get("Commodity").trim();

                    String variety = record.get("Variety").trim();
                    String grade = record.get("Grade").trim();

                    if (variety.isBlank()) {
                        variety = "";
                    }

                    if (grade.isBlank()) {
                        grade = "";
                    }

                    LocalDate date = LocalDate.parse(
                            record.get("Arrival_Date").trim(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    );

                    Double minPrice = parsePrice(record.get("Min_x0020_Price"));
                    Double maxPrice = parsePrice(record.get("Max_x0020_Price"));
                    Double modalPrice = parsePrice(record.get("Modal_x0020_Price"));

                    if (district.isBlank() || marketName.isBlank()
                            || cropName.isBlank() || modalPrice <= 0) {
                        invalid++;
                        continue;
                    }

                    Market market = marketRepository
                            .findByMarketNameIgnoreCaseAndLocationIgnoreCase(
                                    marketName, district)
                            .orElseGet(() -> {
                                Market newMarket = new Market();
                                newMarket.setMarketName(marketName);
                                newMarket.setLocation(district);
                                return marketRepository.save(newMarket);
                            });

                    Crop crop = cropRepository
                            .findByCropNameIgnoreCase(cropName)
                            .orElseGet(() -> {
                                Crop newCrop = new Crop();
                                newCrop.setCropName(cropName);
                                return cropRepository.save(newCrop);
                            });

                    boolean alreadyExists =
                            marketPriceRepository
                                    .existsByMarketMarketIdAndCropCropIdAndDateAndVarietyAndGrade(
                                            market.getMarketId(),
                                            crop.getCropId(),
                                            date,
                                            variety,
                                            grade
                                    );

                    if (alreadyExists) {
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
                }
            }
        }

        Map<String, Object> result = new HashMap<>();

        result.put("message", "Market price CSV import completed");
        result.put("totalRecordsRead", recordsRead);
        result.put("insertedRecords", inserted);
        result.put("duplicateRecordsSkipped", duplicates);
        result.put("otherStatesSkipped", skippedOtherStates);
        result.put("invalidRecordsSkipped", invalid);

        return result;
    }

    private Double parsePrice(String value) {
        return Double.parseDouble(value.trim().replace(",", ""));
    }
}