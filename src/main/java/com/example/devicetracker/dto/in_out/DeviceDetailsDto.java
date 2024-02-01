package com.example.devicetracker.dto.in_out;

import lombok.Data;

import java.util.List;

@Data
public class DeviceDetailsDto {
    private Long deviceId;
    private String deviceName;
    private Long userId;
    private String deviceType;
    private String imeiNumber;
    private List<String> usageTypeList;
}
