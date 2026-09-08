package com.example.smartagriculturebackend.controller;



import com.example.smartagriculturebackend.entity.Farmer;
import com.example.smartagriculturebackend.service.FarmerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farmers")
@CrossOrigin(origins = "http://localhost:5173")
public class FarmerController {

    private final FarmerService farmerService;

    public FarmerController(FarmerService farmerService) {
        this.farmerService = farmerService;
    }

    @PostMapping
    public Farmer addFarmer(@RequestBody Farmer farmer) {
        return farmerService.addFarmer(farmer);
    }

    @GetMapping
    public List<Farmer> getAllFarmers() {
        return farmerService.getAllFarmers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Farmer> getFarmerById(@PathVariable Long id) {

        return farmerService.getFarmerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}