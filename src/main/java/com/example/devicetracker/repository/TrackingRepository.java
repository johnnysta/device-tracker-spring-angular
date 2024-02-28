package com.example.devicetracker.repository;

import com.example.devicetracker.domain.TrackingData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackingRepository extends JpaRepository<TrackingData, Long> {
    List<TrackingData> findAllByDeviceId(Long deviceId);
}
