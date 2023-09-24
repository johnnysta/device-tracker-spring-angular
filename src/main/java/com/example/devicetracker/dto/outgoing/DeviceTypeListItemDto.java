package com.example.devicetracker.dto.outgoing;

import com.example.devicetracker.domain.DeviceType;
import lombok.Data;

@Data
public class DeviceTypeListItemDto {
    private String name;
    private String displayName;


    public DeviceTypeListItemDto(DeviceType deviceType) {
        this.name = deviceType.toString();
        this.displayName = deviceType.getDisplayName();
    }

}
