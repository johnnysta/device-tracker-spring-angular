package com.example.devicetracker.service;

import com.example.devicetracker.domain.*;
import com.example.devicetracker.dto.in.DeviceTrackStatusChangeDto;
import com.example.devicetracker.dto.in_out.DeviceDetailsDto;
import com.example.devicetracker.dto.in_out.TrackingSettingsDataDto;
import com.example.devicetracker.dto.out.DeviceCreationInitDataDto;
import com.example.devicetracker.dto.out.DeviceListItemDto;
import com.example.devicetracker.dto.out.DeviceTypeListItemDto;
import com.example.devicetracker.dto.out.UsageTypeListItemDto;
import com.example.devicetracker.mapping.DeviceMapper;
import com.example.devicetracker.mapping.TrackingSettingsMapper;
import com.example.devicetracker.repository.DeviceRepository;
import com.example.devicetracker.repository.TrackingSettingsRepository;
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
    private TrackingSettingsRepository trackingSettingsRepository;
    private TrackingSettingsMapper trackingSettingsMapper;

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

    public Device findDeviceById(Long deviceId) {
        Device deviceFound = deviceRepository.findById(deviceId).orElseThrow(EntityNotFoundException::new);
        return deviceFound;
    }

    public DeviceDetailsDto findDeviceDetailsDtoById(Long deviceId) {
        Device deviceFound = deviceRepository.findById(deviceId).orElseThrow(EntityNotFoundException::new);
        return deviceMapper.mapFromDeviceToDeviceDetailsDto(deviceFound);
    }

    public void updateDeviceById(Long deviceId, DeviceDetailsDto deviceDetailsDto) {
        Device deviceFound = deviceRepository.findById(deviceId).orElseThrow(EntityNotFoundException::new);
        deviceMapper.mapFromDeviceDetailsDtoToExistingDevice(deviceDetailsDto, deviceFound);
        deviceRepository.save(deviceFound);
    }

    public Device findDeviceByImeiNumber(String imeiNumber) {
        log.info("***IMEI: " + imeiNumber );
        Device deviceFound = deviceRepository.findByImeiNumber(imeiNumber).orElseThrow(EntityNotFoundException::new);
        return deviceFound;
    }

    public TrackingSettingsDataDto findTrackingSettingsDataDtoById(Long deviceId) {
        Device deviceFound = deviceRepository.findById(deviceId).orElseThrow(EntityNotFoundException::new);
//        TrackingSettings trackingSettings = deviceFound.getTrackingSettings();
        TrackingSettings trackingSettings = trackingSettingsRepository.findById(deviceFound.getTrackingSettings().getId()).orElseThrow(EntityNotFoundException::new);
        return trackingSettingsMapper.mapTrackingSettingsToTrackingSettingsDataDto(trackingSettings);
    }


    public void setTrackingSettingsById(Long deviceId, TrackingSettingsDataDto trackingSettingsDataDto) {
        Device deviceFound = deviceRepository.findById(deviceId).orElseThrow(EntityNotFoundException::new);
        //        TrackingSettings trackingSettings = deviceFound.getTrackingSettings();
        TrackingSettings trackingSettings = trackingSettingsRepository.findById(deviceFound.getTrackingSettings().getId()).orElseThrow(EntityNotFoundException::new);
        trackingSettings.setMeteringFrequency(trackingSettingsDataDto.getMeteringFrequency());
        trackingSettings.setIsGeofenceActive(trackingSettingsDataDto.getIsGeofenceActive());
        trackingSettings.setGeofenceRadius(trackingSettingsDataDto.getGeofenceRadius());
        Location geofenceCenter = trackingSettings.getGeofenceCenter();
        if (geofenceCenter == null) {
            geofenceCenter = new Location(trackingSettingsDataDto.getGeofenceCenterLatitude(), trackingSettingsDataDto.getGeofenceCenterLongitude());
            trackingSettings.setGeofenceCenter(geofenceCenter);
        } else {
            geofenceCenter.setLatitude(trackingSettingsDataDto.getGeofenceCenterLatitude());
            geofenceCenter.setLongitude(trackingSettingsDataDto.getGeofenceCenterLongitude());
        }
        trackingSettingsRepository.save(trackingSettings);
    }
}
