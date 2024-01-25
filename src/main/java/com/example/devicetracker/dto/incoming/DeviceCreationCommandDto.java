package com.example.devicetracker.dto.incoming;

import com.example.devicetracker.domain.Account;
import com.example.devicetracker.domain.DeviceType;
import com.example.devicetracker.domain.UsageType;
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
