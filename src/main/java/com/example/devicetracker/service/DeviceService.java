package com.example.devicetracker.service;

import com.example.devicetracker.domain.Account;
import com.example.devicetracker.domain.Device;
import com.example.devicetracker.domain.DeviceType;
import com.example.devicetracker.domain.UsageType;
import com.example.devicetracker.dto.incoming.DeviceCreationCommandDto;
import com.example.devicetracker.dto.incoming.DeviceTrackStatusChangeDto;
import com.example.devicetracker.dto.outgoing.DeviceCreationInitDataDto;
import com.example.devicetracker.dto.outgoing.DeviceListItemDto;
import com.example.devicetracker.dto.outgoing.DeviceTypeListItemDto;
import com.example.devicetracker.dto.outgoing.UsageTypeListItemDto;
import com.example.devicetracker.mapping.DeviceMapper;
import com.example.devicetracker.repository.DeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class DeviceService {

    private DeviceRepository deviceRepository;
    private DeviceMapper deviceMapper;
    private AccountService accountService;

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

    public void setTrackedStatus(DeviceTrackStatusChangeDto deviceTrackStatusChangeDto) {
        log.info(deviceTrackStatusChangeDto.getDeviceId().toString());
        Device foundDevice = deviceRepository.findById(deviceTrackStatusChangeDto.getDeviceId()).orElseThrow(EntityNotFoundException::new);
        foundDevice.setTracked(deviceTrackStatusChangeDto.getIsTracked());
        deviceRepository.save(foundDevice);
    }

    public void registerDevice(DeviceCreationCommandDto deviceCreationCommandDto) {
        Account user = accountService.findAccountById(deviceCreationCommandDto.getUserId());
        deviceRepository.save(deviceMapper.mapFromDeviceCreationDtoToDevice(deviceCreationCommandDto, user));
    }
}
