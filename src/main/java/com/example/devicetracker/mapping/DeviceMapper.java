package com.example.devicetracker.mapping;

import com.example.devicetracker.domain.*;
import com.example.devicetracker.dto.in_out.DeviceDetailsDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DeviceMapper {

    public Device mapFromDeviceDetailsDtoToDevice(DeviceDetailsDto deviceDetailsDto, Account user) {
        Device device = new Device();
        device.setDeviceName(deviceDetailsDto.getDeviceName());
        device.setDeviceType(DeviceType.valueOf(deviceDetailsDto.getDeviceType()));
        device.setImeiNumber(deviceDetailsDto.getImeiNumber());
        device.setUser(user);
        device.setIsTracked(false);
        deviceDetailsDto.getUsageTypeList().forEach(usageType -> device.getUsageTypeList().add(UsageType.valueOf(usageType)));
        device.setTrackingSettings(new TrackingSettings());
        return device;
    }

    public DeviceDetailsDto mapFromDeviceToDeviceDetailsDto(Device device) {
        DeviceDetailsDto deviceDetailsDto = new DeviceDetailsDto();
        deviceDetailsDto.setDeviceId(device.getId());
        deviceDetailsDto.setDeviceName(device.getDeviceName());
        deviceDetailsDto.setDeviceType(device.getDeviceType().toString());
        deviceDetailsDto.setImeiNumber(device.getImeiNumber());
        deviceDetailsDto.setUserId(device.getUser().getId());
        deviceDetailsDto.setUsageTypeList(device.getUsageTypeList().stream().map(Enum::toString).toList());
        return deviceDetailsDto;
    }

    public void mapFromDeviceDetailsDtoToExistingDevice(DeviceDetailsDto deviceDetailsDto, Device deviceFound) {
        deviceFound.setUsageTypeList(new ArrayList<>());
        deviceFound.setDeviceName(deviceDetailsDto.getDeviceName());
        deviceFound.setImeiNumber(deviceDetailsDto.getImeiNumber());
        deviceFound.setDeviceType(DeviceType.valueOf(deviceDetailsDto.getDeviceType()));
        deviceDetailsDto.getUsageTypeList().forEach(usageType -> deviceFound.getUsageTypeList().add(UsageType.valueOf(usageType)));
    }
}
