package com.example.smartagriculturebackend.repository;

import com.example.smartagriculturebackend.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {

    List<Market> findByLocationContainingIgnoreCase(String location);
    Optional<Market> findByMarketNameIgnoreCaseAndLocationIgnoreCase(
            String marketName, String location);
}