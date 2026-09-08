package com.example.smartagriculturebackend.controller;


import com.example.smartagriculturebackend.entity.Crop;
import com.example.smartagriculturebackend.entity.CropRequirements;
import com.example.smartagriculturebackend.service.CropRequirementsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crop-requirements")
@CrossOrigin(origins = "http://localhost:5173")
public class CropRequirementsController {

    private final CropRequirementsService service;

    public CropRequirementsController(
            CropRequirementsService service) {

        this.service = service;
    }

    @PostMapping
    public CropRequirements addRequirement(
            @RequestBody CropRequirementRequest request) {

        Crop crop = new Crop();
        crop.setCropId(request.getCropId());

        CropRequirements requirement =
                new CropRequirements();

        requirement.setCrop(crop);
        requirement.setSoilType(request.getSoilType());
        requirement.setMinPh(request.getMinPh());
        requirement.setMaxPh(request.getMaxPh());

        return service.addRequirement(requirement);
    }

    @GetMapping
    public List<CropRequirements> getAllRequirements() {
        return service.getAllRequirements();
    }

    @GetMapping("/crop/{cropId}")
    public List<CropRequirements> getRequirementsByCrop(
            @PathVariable Long cropId) {

        return service.getRequirementsByCrop(cropId);
    }

    public static class CropRequirementRequest {

        private Long cropId;
        private String soilType;
        private Double minPh;
        private Double maxPh;

        public Long getCropId() {
            return cropId;
        }

        public void setCropId(Long cropId) {
            this.cropId = cropId;
        }

        public String getSoilType() {
            return soilType;
        }

        public void setSoilType(String soilType) {
            this.soilType = soilType;
        }

        public Double getMinPh() {
            return minPh;
        }

        public void setMinPh(Double minPh) {
            this.minPh = minPh;
        }

        public Double getMaxPh() {
            return maxPh;
        }

        public void setMaxPh(Double maxPh) {
            this.maxPh = maxPh;
        }
    }
}

