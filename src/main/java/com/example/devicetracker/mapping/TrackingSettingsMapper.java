package com.example.devicetracker.mapping;

import com.example.devicetracker.domain.TrackingSettings;
import com.example.devicetracker.dto.in_out.TrackingSettingsDataDto;
import org.springframework.stereotype.Component;

@Component
public class TrackingSettingsMapper {

    public TrackingSettingsDataDto mapTrackingSettingsToTrackingSettingsDataDto(TrackingSettings trackingSettings) {
        TrackingSettingsDataDto trackingSettingsDataDto = new TrackingSettingsDataDto();
        trackingSettingsDataDto.setMeteringFrequency(trackingSettings.getMeteringFrequency());
        trackingSettingsDataDto.setIsGeofenceActive(trackingSettings.getIsGeofenceActive());
        trackingSettingsDataDto.setGeofenceRadius(trackingSettings.getGeofenceRadius());
        if (trackingSettings.getGeofenceCenter() != null) {
            trackingSettingsDataDto.setGeofenceCenterLatitude(trackingSettings.getGeofenceCenter().getLatitude());
            trackingSettingsDataDto.setGeofenceCenterLongitude(trackingSettings.getGeofenceCenter().getLongitude());
        }
        return trackingSettingsDataDto;
    }
}
