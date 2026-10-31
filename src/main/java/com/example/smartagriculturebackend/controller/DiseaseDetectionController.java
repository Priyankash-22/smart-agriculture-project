
package com.example.smartagriculturebackend.controller;

import com.example.smartagriculturebackend.service.DiseaseDetectionService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/disease-detection")
@CrossOrigin(origins = "*")
public class DiseaseDetectionController {

    private final DiseaseDetectionService diseaseDetectionService;

    public DiseaseDetectionController(
            DiseaseDetectionService diseaseDetectionService) {
        this.diseaseDetectionService = diseaseDetectionService;
    }

    // Upload image and predict disease
    @PostMapping(
            value = "/predict",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Map<String, Object>> detectDisease(
            @RequestParam("file") MultipartFile file,
            @RequestParam("farmerId") Long farmerId)
            throws IOException {

        Map<String, Object> result =
                diseaseDetectionService.detectDisease(file, farmerId);

        return ResponseEntity.ok(result);
    }

    // Retrieve farmer history with disease information
    @GetMapping("/history/{farmerId}")
    public ResponseEntity<List<Map<String, Object>>> getHistory(
            @PathVariable Long farmerId) {

        List<Map<String, Object>> history =
                diseaseDetectionService.getHistory(farmerId);

        return ResponseEntity.ok(history);
    }
}
