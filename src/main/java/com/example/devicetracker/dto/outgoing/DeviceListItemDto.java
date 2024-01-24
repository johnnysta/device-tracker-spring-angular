package com.example.devicetracker.dto.outgoing;

import com.example.devicetracker.domain.Device;
import com.example.devicetracker.domain.DeviceType;
import com.example.devicetracker.domain.UsageType;
import lombok.Data;

import java.util.List;

@Data
public class DeviceListItemDto {
    private String deviceName;
    private Long userId;
    private DeviceType deviceType;
    private String imeiNumber;
    private List<UsageType> usageTypeList;

    public DeviceListItemDto(Device device) {
        this.deviceName = device.getDeviceName();
        this.userId = device.getUser().getId();
        this.deviceType = device.getDeviceType();
        this.imeiNumber = device.getImeiNumber();
        this.usageTypeList = device.getUsageTypeList();
    }
}
