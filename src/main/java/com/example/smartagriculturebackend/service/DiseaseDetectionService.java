
package com.example.smartagriculturebackend.service;

import com.example.smartagriculturebackend.entity.DiseaseDetection;
import com.example.smartagriculturebackend.repository.DiseaseDetectionRepository;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiseaseDetectionService {

    private final DiseaseDetectionRepository repository;
    private final RestClient restClient = RestClient.create();

    public DiseaseDetectionService(
            DiseaseDetectionRepository repository) {
        this.repository = repository;
    }

    // Detect disease using Python AI service
    public Map<String, Object> detectDisease(
            MultipartFile file, Long farmerId) throws IOException {

        ByteArrayResource imageResource =
                new ByteArrayResource(file.getBytes()) {
                    @Override
                    public String getFilename() {
                        return file.getOriginalFilename() != null
                                ? file.getOriginalFilename()
                                : "leaf-image.jpg";
                    }
                };

        MultiValueMap<String, Object> body =
                new LinkedMultiValueMap<>();

        body.add("file", imageResource);

        Map<String, Object> result = restClient.post()
                .uri("http://localhost:8000/predict")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .body(Map.class);

        if (result == null) {
            throw new IllegalStateException(
                    "No response received from AI service");
        }

        DiseaseDetection detection = new DiseaseDetection();

        detection.setFarmerId(farmerId);
        detection.setImageName(file.getOriginalFilename());
        detection.setStatus((String) result.get("status"));

        Object confidence = result.get("confidence");

        if (confidence instanceof Number) {
            detection.setConfidence(
                    ((Number) confidence).doubleValue());
        }

        String plant = (String) result.get("plant");
        String disease = (String) result.get("disease");

        // Get top prediction when the result is uncertain
        if (disease == null && result.get("predictions") instanceof List) {

            List<?> predictions = (List<?>) result.get("predictions");

            if (!predictions.isEmpty()
                    && predictions.get(0) instanceof Map) {

                Map<?, ?> firstPrediction =
                        (Map<?, ?>) predictions.get(0);

                disease = (String) firstPrediction.get("label");
            }
        }

        if (plant == null && disease != null
                && disease.contains("___")) {
            plant = disease.split("___", 2)[0];
        }

        detection.setPlant(plant);
        detection.setPredictedDisease(disease);

        repository.save(detection);

        return result;
    }

    // Get disease detection history with disease information
    public List<Map<String, Object>> getHistory(Long farmerId) {

        List<DiseaseDetection> records =
                repository.findByFarmerId(farmerId);

        List<Map<String, Object>> history = new ArrayList<>();

        for (DiseaseDetection detection : records) {

            Map<String, Object> record = new LinkedHashMap<>();

            record.put("detectionId", detection.getDetectionId());
            record.put("farmerId", detection.getFarmerId());
            record.put("plant", detection.getPlant());
            record.put("predictedDisease",
                    detection.getPredictedDisease());
            record.put("confidence", detection.getConfidence());
            record.put("status", detection.getStatus());
            record.put("imageName", detection.getImageName());
            record.put("detectionDate", detection.getDetectionDate());

            String disease = detection.getPredictedDisease();

            if (disease != null && !disease.isBlank()) {

                try {
                    String url = UriComponentsBuilder
                            .fromUriString("http://localhost:8000")
                            .path("/disease-info/")
                            .path(disease)
                            .build()
                            .encode()
                            .toUriString();

                    Map<String, Object> information =
                            restClient.get()
                                    .uri(url)
                                    .retrieve()
                                    .body(Map.class);

                    if (information != null) {
                        information.remove("status");
                        record.putAll(information);
                    }

                } catch (Exception exception) {
                    record.put("informationMessage",
                            "Disease information is currently unavailable.");
                }
            }

            history.add(record);
        }

        return history;
    }
}
