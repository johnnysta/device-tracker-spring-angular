package com.example.devicetracker.service;

import com.example.devicetracker.domain.Account;
import com.example.devicetracker.domain.Device;
import com.example.devicetracker.domain.DeviceType;
import com.example.devicetracker.domain.UsageType;
import com.example.devicetracker.dto.in.DeviceTrackStatusChangeDto;
import com.example.devicetracker.dto.in_out.DeviceDetailsDto;
import com.example.devicetracker.dto.out.DeviceCreationInitDataDto;
import com.example.devicetracker.dto.out.DeviceListItemDto;
import com.example.devicetracker.dto.out.DeviceTypeListItemDto;
import com.example.devicetracker.dto.out.UsageTypeListItemDto;
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
            log.info((device.getIsTracked()) ? "tracked:  true" : "tracked: false");
        });
        return resultDevicesDto;
    }

    public void setTrackedStatus(DeviceTrackStatusChangeDto deviceTrackStatusChangeDto) {
        log.info(deviceTrackStatusChangeDto.getDeviceId().toString());
        Device deviceFound = deviceRepository.findById(deviceTrackStatusChangeDto.getDeviceId()).orElseThrow(EntityNotFoundException::new);
        deviceFound.setIsTracked(deviceTrackStatusChangeDto.getIsTracked());
        deviceRepository.save(deviceFound);
    }

    public void registerDevice(DeviceDetailsDto deviceDetailsDto) {
        Account user = accountService.findAccountById(deviceDetailsDto.getUserId());
        deviceRepository.save(deviceMapper.mapFromDeviceDetailsDtoToDevice(deviceDetailsDto, user));
    }

    public void deleteDeviceById(Long deviceId) {
        deviceRepository.deleteById(deviceId);
        //TODO delete all device related tracking data
    }

    public DeviceDetailsDto findDeviceById(Long deviceId) {
        Device deviceFound = deviceRepository.findById(deviceId).orElseThrow(EntityNotFoundException::new);
        return deviceMapper.mapFromDeviceToDeviceDetailsDto(deviceFound);
    }

    public void updateDeviceById(Long deviceId, DeviceDetailsDto deviceDetailsDto) {
        Device deviceFound = deviceRepository.findById(deviceId).orElseThrow(EntityNotFoundException::new);
        deviceMapper.mapFromDeviceDetailsDtoToExistingDevice(deviceDetailsDto, deviceFound);
        deviceRepository.save(deviceFound);
    }
}
