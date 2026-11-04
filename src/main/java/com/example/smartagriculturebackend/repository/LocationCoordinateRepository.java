
package com.example.smartagriculturebackend.repository;

import com.example.smartagriculturebackend.entity.LocationCoordinate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationCoordinateRepository
        extends JpaRepository<LocationCoordinate, Long> {

    Optional<LocationCoordinate> findByLocationNameIgnoreCase(
            String locationName);
}

