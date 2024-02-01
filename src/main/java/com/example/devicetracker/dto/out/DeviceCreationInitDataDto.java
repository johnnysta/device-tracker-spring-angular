package com.example.devicetracker.dto.out;

import lombok.Data;

import java.util.List;
@Data
public class DeviceCreationInitDataDto {

    private List<DeviceTypeListItemDto> deviceTypeList;
    private List<UsageTypeListItemDto> usageTypeList;

    public DeviceCreationInitDataDto(List<DeviceTypeListItemDto> deviceTypeList, List<UsageTypeListItemDto> usageTypeList) {
        this.deviceTypeList = deviceTypeList;
        this.usageTypeList = usageTypeList;
    }
}
