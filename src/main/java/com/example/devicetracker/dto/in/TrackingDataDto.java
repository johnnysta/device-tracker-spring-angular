package com.example.devicetracker.dto.in;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrackingDataDto {

    private String imeiNumber;
    private Double latitude;
    private Double longitude;
    private LocalDateTime dateTime;

}
