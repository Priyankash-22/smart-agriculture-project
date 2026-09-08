package com.example.smartagriculturebackend.controller;

import com.example.smartagriculturebackend.entity.Crop;
import com.example.smartagriculturebackend.service.CropService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
@CrossOrigin(origins = "http://localhost:5173")
public class CropController {

    private final CropService cropService;

    public CropController(CropService cropService) {
        this.cropService = cropService;
    }

    @PostMapping
    public Crop addCrop(@RequestBody Crop crop) {
        return cropService.addCrop(crop);
    }

    @GetMapping
    public List<Crop> getAllCrops() {
        return cropService.getAllCrops();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Crop> getCropById(
            @PathVariable Long id) {

        return cropService.getCropById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
