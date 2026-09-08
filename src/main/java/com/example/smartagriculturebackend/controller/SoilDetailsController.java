package com.example.smartagriculturebackend.controller;


import com.example.smartagriculturebackend.entity.Farmer;
import com.example.smartagriculturebackend.entity.SoilDetails;
import com.example.smartagriculturebackend.service.SoilDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/soil")
@CrossOrigin(origins = "http://localhost:5173")
public class SoilDetailsController {

    private final SoilDetailsService soilDetailsService;

    public SoilDetailsController(SoilDetailsService soilDetailsService) {
        this.soilDetailsService = soilDetailsService;
    }

    @PostMapping
    public SoilDetails addSoilDetails(@RequestBody SoilDetailsRequest request) {

        Farmer farmer = new Farmer();
        farmer.setFarmerId(request.getFarmerId());

        SoilDetails soilDetails = new SoilDetails();

        soilDetails.setFarmer(farmer);
        soilDetails.setSoilType(request.getSoilType());
        soilDetails.setPh(request.getPh());
        soilDetails.setSeason(request.getSeason());
        soilDetails.setWaterAvailability(request.getWaterAvailability());

        return soilDetailsService.addSoilDetails(soilDetails);
    }

    @GetMapping("/farmer/{farmerId}")
    public List<SoilDetails> getSoilDetailsByFarmer(
            @PathVariable Long farmerId) {

        return soilDetailsService.getSoilDetailsByFarmer(farmerId);
    }

    @GetMapping
    public List<SoilDetails> getAllSoilDetails() {
        return soilDetailsService.getAllSoilDetails();
    }

    public static class SoilDetailsRequest {

        private Long farmerId;
        private String soilType;
        private Double ph;
        private String season;
        private String waterAvailability;

        public Long getFarmerId() {
            return farmerId;
        }

        public void setFarmerId(Long farmerId) {
            this.farmerId = farmerId;
        }

        public String getSoilType() {
            return soilType;
        }

        public void setSoilType(String soilType) {
            this.soilType = soilType;
        }

        public Double getPh() {
            return ph;
        }

        public void setPh(Double ph) {
            this.ph = ph;
        }

        public String getSeason() {
            return season;
        }

        public void setSeason(String season) {
            this.season = season;
        }

        public String getWaterAvailability() {
            return waterAvailability;
        }

        public void setWaterAvailability(String waterAvailability) {
            this.waterAvailability = waterAvailability;
        }
    }
}