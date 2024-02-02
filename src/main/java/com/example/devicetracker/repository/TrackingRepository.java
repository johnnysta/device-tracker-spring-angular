package com.example.devicetracker.repository;

import com.example.devicetracker.domain.TrackingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TrackingRepository extends JpaRepository<TrackingData, Long> {
}
