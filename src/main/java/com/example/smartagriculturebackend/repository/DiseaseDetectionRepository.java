
package com.example.smartagriculturebackend.repository;

import com.example.smartagriculturebackend.entity.DiseaseDetection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseDetectionRepository
        extends JpaRepository<DiseaseDetection, Long> {

    List<DiseaseDetection> findByFarmerId(Long farmerId);
}
