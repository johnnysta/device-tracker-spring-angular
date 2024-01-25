package com.example.devicetracker.dto.incoming;

import lombok.Data;

@Data
public class DeviceTrackStatusChangeDto {
    Long deviceId;
    Boolean isTracked;
}
