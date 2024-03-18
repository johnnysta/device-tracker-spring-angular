package com.example.devicetracker.controller;

import com.example.devicetracker.dto.in.DeviceTrackStatusChangeDto;
import com.example.devicetracker.dto.in_out.DeviceDetailsDto;
import com.example.devicetracker.dto.in_out.TrackingSettingsDataDto;
import com.example.devicetracker.dto.out.DeviceCreationInitDataDto;
import com.example.devicetracker.dto.out.DeviceListItemDto;
import com.example.devicetracker.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
@Slf4j
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/init-data")
    public ResponseEntity<DeviceCreationInitDataDto> getInitData() {
        DeviceCreationInitDataDto initData = deviceService.getInitData();
        return new ResponseEntity<>(initData, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> registerDevice(@RequestBody DeviceDetailsDto deviceDetailsDto) {
        deviceService.registerDevice(deviceDetailsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{deviceId}")
    public ResponseEntity<Void> deletDevice(@PathVariable Long deviceId) {
        deviceService.deleteDeviceById(deviceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/devices_by_user/{userId}")
    public ResponseEntity<List<DeviceListItemDto>> getDevicesByUserId(@PathVariable Long userId) {
        List<DeviceListItemDto> results = deviceService.getDevicesByUserId(userId);
        results.forEach(device -> {
            log.info((device.getIsTracked()) ? "tracked:  true" : "tracked: false");
        });
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("/device_change_track_status")
    public ResponseEntity<Void> setTrackedStatus(@RequestBody DeviceTrackStatusChangeDto deviceTrackStatusChangeDto) {
        deviceService.setTrackedStatus(deviceTrackStatusChangeDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/device_by_id/{deviceId}")
    public ResponseEntity<DeviceDetailsDto> findDeviceById(@PathVariable Long deviceId) {
        DeviceDetailsDto deviceDetailsDto = deviceService.findDeviceDetailsDtoById(deviceId);
        return new ResponseEntity<>(deviceDetailsDto, HttpStatus.OK);
    }

    @PutMapping("{deviceId}")
    public ResponseEntity<Void> updateDeviceById(@PathVariable Long deviceId, @RequestBody DeviceDetailsDto deviceDetailsDto) {
        deviceService.updateDeviceById(deviceId, deviceDetailsDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/trackingSettings/{deviceId}")
    public ResponseEntity<TrackingSettingsDataDto> getTrackingSettingsByDeviceId(@PathVariable Long deviceId) {
        TrackingSettingsDataDto trackingSettingsDataDto = deviceService.findTrackingSettingsDataDtoById(deviceId);
        return new ResponseEntity<>(trackingSettingsDataDto, HttpStatus.OK);
    }


    @PutMapping("/trackingSettings/{deviceId}")
    public ResponseEntity<Void> updateTrackingSettingsByDeviceId(@PathVariable Long deviceId, @RequestBody TrackingSettingsDataDto trackingSettingsDataDto) {
        deviceService.setTrackingSettingsById(deviceId, trackingSettingsDataDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/trackingSettingsByDevice/{deviceIMEI}")
    public ResponseEntity<TrackingSettingsDataDto> getTrackingSettingsByDeviceImei(@PathVariable String deviceIMEI) {
        TrackingSettingsDataDto trackingSettingsDataDto = deviceService.getTrackingSettingsByDeviceImei(deviceIMEI);
        return new ResponseEntity<>(trackingSettingsDataDto, HttpStatus.OK);
    }


}
