package com.example.smartagriculturebackend.repository;

import com.example.smartagriculturebackend.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {
}
