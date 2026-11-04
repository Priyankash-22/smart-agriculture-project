package com.example.smartagriculturebackend.repository;



import com.example.smartagriculturebackend.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface CropRepository extends JpaRepository<Crop, Long> {
    Optional<Crop> findByCropNameIgnoreCase(String cropName);
}
