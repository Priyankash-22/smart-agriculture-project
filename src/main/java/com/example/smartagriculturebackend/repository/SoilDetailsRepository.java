package com.example.smartagriculturebackend.repository;

import com.example.smartagriculturebackend.entity.SoilDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoilDetailsRepository extends JpaRepository<SoilDetails, Long> {

    List<SoilDetails> findByFarmerFarmerId(Long farmerId);
}

