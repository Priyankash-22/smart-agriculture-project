package com.example.smartagriculturebackend.repository;

import com.example.smartagriculturebackend.entity.MarketPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDate;

@Repository
public interface MarketPriceRepository extends JpaRepository<MarketPrice, Long> {

    List<MarketPrice> findByCropCropId(Long cropId);

    List<MarketPrice> findByMarketMarketId(Long marketId);

    boolean existsByMarketMarketIdAndCropCropIdAndDateAndVarietyAndGrade(
            Long marketId, Long cropId, LocalDate date,
            String variety, String grade);
}
