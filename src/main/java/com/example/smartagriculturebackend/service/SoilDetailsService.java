package com.example.smartagriculturebackend.service;



import com.example.smartagriculturebackend.entity.SoilDetails;
import com.example.smartagriculturebackend.repository.SoilDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoilDetailsService {

    private final SoilDetailsRepository soilDetailsRepository;

    public SoilDetailsService(SoilDetailsRepository soilDetailsRepository) {
        this.soilDetailsRepository = soilDetailsRepository;
    }

    public SoilDetails addSoilDetails(SoilDetails soilDetails) {
        return soilDetailsRepository.save(soilDetails);
    }

    public List<SoilDetails> getSoilDetailsByFarmer(Long farmerId) {
        return soilDetailsRepository.findByFarmerFarmerId(farmerId);
    }

    public List<SoilDetails> getAllSoilDetails() {
        return soilDetailsRepository.findAll();
    }
}