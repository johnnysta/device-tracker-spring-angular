package com.example.devicetracker.dto.in;

import lombok.Data;

import java.util.List;

@Data
public class DeviceCreationCommandDto {
    private String deviceName;
    private Long userId;
    private String deviceType;
    private String imeiNumber;
    private List<String> usageTypeList;
}
