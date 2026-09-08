package com.example.smartagriculturebackend.repository;
import com.example.smartagriculturebackend.entity.CropRequirements;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CropRequirementsRepository
        extends JpaRepository<CropRequirements, Long> {

    List<CropRequirements> findByCropCropId(Long cropId);

    List<CropRequirements> findBySoilTypeIgnoreCaseAndSeasonIgnoreCaseAndMinPhLessThanEqualAndMaxPhGreaterThanEqual(
            String soilType,
            String season,
            Double ph1,
            Double ph2
    );
}
