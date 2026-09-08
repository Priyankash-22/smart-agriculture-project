package com.example.smartagriculturebackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "soil_details")
public class SoilDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "soil_id")
    private Long soilId;

    @ManyToOne
    @JoinColumn(name = "farmer_id", nullable = false)
    private Farmer farmer;

    @Column(name = "soil_type", nullable = false)
    private String soilType;

    @Column(nullable = false)
    private Double ph;

    @Column(nullable = false)
    private String season;

    @Column(name = "water_availability", nullable = false)
    private String waterAvailability;

    public SoilDetails() {
    }

    public Long getSoilId() {
        return soilId;
    }

    public void setSoilId(Long soilId) {
        this.soilId = soilId;
    }

    public Farmer getFarmer() {
        return farmer;
    }

    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
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
