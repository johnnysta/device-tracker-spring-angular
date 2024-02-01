package com.example.devicetracker.dto.in;

import lombok.Data;

@Data
public class DeviceTrackStatusChangeDto {
    Long deviceId;
    Boolean isTracked;
}
