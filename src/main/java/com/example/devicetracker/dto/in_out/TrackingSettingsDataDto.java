package com.example.devicetracker.dto.in_out;
import lombok.Data;

@Data
public class TrackingSettingsDataDto {
    private Integer meteringFrequency;
    private Boolean isGeofenceActive;
    private Double geofenceCenterLatitude;
    private Double geofenceCenterLongitude;
    private Integer geofenceRadius;

}
