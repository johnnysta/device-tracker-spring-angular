package com.example.devicetracker.service;

import com.example.devicetracker.domain.Device;
import com.example.devicetracker.domain.DeviceType;
import com.example.devicetracker.domain.UsageType;
import com.example.devicetracker.dto.outgoing.DeviceCreationInitDataDto;
import com.example.devicetracker.dto.outgoing.DeviceListItemDto;
import com.example.devicetracker.dto.outgoing.DeviceTypeListItemDto;
import com.example.devicetracker.dto.outgoing.UsageTypeListItemDto;
import com.example.devicetracker.repository.DeviceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class DeviceService {

    private DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public DeviceCreationInitDataDto getInitData() {
        return new DeviceCreationInitDataDto(getDeviceTypeList(), getUsageTypeList());
    }

    private List<DeviceTypeListItemDto> getDeviceTypeList() {
        List<DeviceTypeListItemDto> deviceTypeList = new ArrayList<>();
        for (DeviceType deviceType : DeviceType.values()) {
            deviceTypeList.add(new DeviceTypeListItemDto(deviceType));
        }
        return deviceTypeList;
    }

    private List<UsageTypeListItemDto> getUsageTypeList() {
        List<UsageTypeListItemDto> usageTypeList = new ArrayList<>();
        for (UsageType usageType : UsageType.values()) {
            usageTypeList.add(new UsageTypeListItemDto(usageType));
        }
        return usageTypeList;
    }


    public List<DeviceListItemDto> getDevicesByUserId(Long id) {
        List<Device> resultDevices = deviceRepository.findAllByUserId(id);
        List<DeviceListItemDto> resultDevicesDto = new ArrayList<>();
        resultDevices.forEach(device -> {
            resultDevicesDto.add(new DeviceListItemDto(device));
        });
        return resultDevicesDto;
    }
}
