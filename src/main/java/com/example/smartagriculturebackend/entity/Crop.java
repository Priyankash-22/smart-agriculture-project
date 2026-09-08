package com.example.smartagriculturebackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "crop")
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crop_id")
    private Long cropId;

    @Column(name = "crop_name", nullable = false)
    private String cropName;

    public Crop() {
    }

    public Long getCropId() {
        return cropId;
    }

    public void setCropId(Long cropId) {
        this.cropId = cropId;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }
}