package com.example.devicetracker.mapping;

import com.example.devicetracker.domain.Account;
import com.example.devicetracker.domain.Device;
import com.example.devicetracker.domain.DeviceType;
import com.example.devicetracker.domain.UsageType;
import com.example.devicetracker.dto.incoming.DeviceCreationCommandDto;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {

    public Device mapFromDeviceCreationDtoToDevice(DeviceCreationCommandDto deviceCreationCommandDto, Account user) {
        Device device = new Device();
        device.setDeviceName(deviceCreationCommandDto.getDeviceName());
        device.setDeviceType(DeviceType.valueOf(deviceCreationCommandDto.getDeviceType()));
        device.setImeiNumber(deviceCreationCommandDto.getImeiNumber());
        device.setUser(user);
        device.setTracked(false);
        deviceCreationCommandDto.getUsageTypeList().forEach(usageType -> device.getUsageTypeList().add(UsageType.valueOf(usageType)));
        return device;
    }

}
