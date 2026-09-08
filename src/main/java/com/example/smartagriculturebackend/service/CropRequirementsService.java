package com.example.smartagriculturebackend.service;
import com.example.smartagriculturebackend.entity.CropRequirements;
import com.example.smartagriculturebackend.repository.CropRequirementsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropRequirementsService {

    private final CropRequirementsRepository repository;

    public CropRequirementsService(CropRequirementsRepository repository) {
        this.repository = repository;
    }

    public CropRequirements addRequirement(
            CropRequirements requirement) {

        return repository.save(requirement);
    }

    public List<CropRequirements> getAllRequirements() {
        return repository.findAll();
    }

    public List<CropRequirements> getRequirementsByCrop(Long cropId) {
        return repository.findByCropCropId(cropId);
    }
}

