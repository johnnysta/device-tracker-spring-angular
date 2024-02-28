package com.example.devicetracker.repository;

import com.example.devicetracker.domain.TrackingSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackingSettingsRepository extends JpaRepository<TrackingSettings, Long> {


}
