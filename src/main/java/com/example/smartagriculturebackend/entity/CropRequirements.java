package com.example.smartagriculturebackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "crop_requirements")
public class CropRequirements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requirement_id")
    private Long requirementId;

    @ManyToOne
    @JoinColumn(name = "crop_id", nullable = false)
    private Crop crop;

    @Column(name = "soil_type", nullable = false)
    private String soilType;

    @Column(name = "min_ph", nullable = false)
    private Double minPh;

    @Column(name = "max_ph", nullable = false)
    private Double maxPh;

    @Column(nullable = false)
    private String season;

    public CropRequirements() {
    }

    public Long getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(Long requirementId) {
        this.requirementId = requirementId;
    }

    public Crop getCrop() {
        return crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
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
    public String getSeason() {
        return season;
    }
    public void setSeason(String season) {
        this.season = season;
    }

}
