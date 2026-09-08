package com.example.smartagriculturebackend.service;
import com.example.smartagriculturebackend.entity.Crop;
import com.example.smartagriculturebackend.repository.CropRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CropService {

    private final CropRepository cropRepository;

    public CropService(CropRepository cropRepository) {
        this.cropRepository = cropRepository;
    }

    public Crop addCrop(Crop crop) {
        return cropRepository.save(crop);
    }

    public List<Crop> getAllCrops() {
        return cropRepository.findAll();
    }

    public Optional<Crop> getCropById(Long id) {
        return cropRepository.findById(id);
    }
}

