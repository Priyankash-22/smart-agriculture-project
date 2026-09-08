package com.example.smartagriculturebackend.service;


import com.example.smartagriculturebackend.entity.Crop;
import com.example.smartagriculturebackend.entity.SoilDetails;
import com.example.smartagriculturebackend.repository.CropRequirementsRepository;
import com.example.smartagriculturebackend.repository.SoilDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService {

    private final SoilDetailsRepository soilDetailsRepository;
    private final CropRequirementsRepository cropRequirementsRepository;

    public RecommendationService(
            SoilDetailsRepository soilDetailsRepository,
            CropRequirementsRepository cropRequirementsRepository) {

        this.soilDetailsRepository = soilDetailsRepository;
        this.cropRequirementsRepository = cropRequirementsRepository;
    }

    public List<Crop> recommendCrops(Long farmerId) {

        List<SoilDetails> soilDetailsList =
                soilDetailsRepository.findByFarmerFarmerId(farmerId);

        if (soilDetailsList.isEmpty()) {
            return new ArrayList<>();
        }

        SoilDetails soilDetails = soilDetailsList.get(0);

        String soilType = soilDetails.getSoilType();
        Double ph = soilDetails.getPh();

        String season = soilDetails.getSeason();

        var requirements =
                cropRequirementsRepository
                        .findBySoilTypeIgnoreCaseAndSeasonIgnoreCaseAndMinPhLessThanEqualAndMaxPhGreaterThanEqual(
                                soilType,
                                season,
                                ph,
                                ph
                        );

        List<Crop> recommendedCrops = new ArrayList<>();

        for (var requirement : requirements) {
            recommendedCrops.add(requirement.getCrop());
        }

        return recommendedCrops;
    }
}
