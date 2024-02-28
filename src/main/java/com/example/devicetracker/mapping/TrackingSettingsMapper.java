package com.example.devicetracker.mapping;

import com.example.devicetracker.domain.TrackingSettings;
import com.example.devicetracker.dto.in_out.TrackingSettingsDataDto;
import org.springframework.stereotype.Component;

@Component
public class TrackingSettingsMapper {

    public TrackingSettingsDataDto mapTrackingSettingsToTrackingSettingsDataDto(TrackingSettings trackingSettings) {
        TrackingSettingsDataDto trackingSettingsDataDto = new TrackingSettingsDataDto();
        trackingSettingsDataDto.setMeteringFrequency(trackingSettings.getMeteringFrequency());
        trackingSettingsDataDto.setGeofenceRadius(trackingSettings.getGeofenceRadius());
        trackingSettingsDataDto.setGeofenceCenterLatitude(trackingSettings.getGeofenceCenter().getLatitude());
        trackingSettingsDataDto.setGeofenceCenterLatitude(trackingSettings.getGeofenceCenter().getLongitude());
        return trackingSettingsDataDto;
    }
}
