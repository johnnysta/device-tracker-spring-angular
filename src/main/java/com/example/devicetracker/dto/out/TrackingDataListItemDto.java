package com.example.devicetracker.dto.out;


import lombok.Data;

@Data
public class TrackingDataListItemDto {

    String dateTime;
    Double latitude;
    Double longitude;
    Long deviceId;
    String deviceName;

}
