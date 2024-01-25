package com.example.devicetracker.controller;

import com.example.devicetracker.dto.incoming.DeviceCreationCommandDto;
import com.example.devicetracker.dto.incoming.DeviceTrackStatusChangeDto;
import com.example.devicetracker.dto.outgoing.DeviceCreationInitDataDto;
import com.example.devicetracker.dto.outgoing.DeviceListItemDto;
import com.example.devicetracker.dto.outgoing.DeviceTypeListItemDto;
import com.example.devicetracker.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/init-data")
    public ResponseEntity<DeviceCreationInitDataDto> getInitData() {
        DeviceCreationInitDataDto initData = deviceService.getInitData();
        return new ResponseEntity<>(initData, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> registerDevice(@RequestBody DeviceCreationCommandDto deviceCreationCommandDto){
        deviceService.registerDevice(deviceCreationCommandDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/devices_by_user/{userId}")
    public ResponseEntity<List<DeviceListItemDto>> getDevicesByUserId(@PathVariable Long userId) {
        List<DeviceListItemDto> results = deviceService.getDevicesByUserId(userId);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("/device_change_track_status")
    public ResponseEntity<Void> setTrackedStatus(@RequestBody DeviceTrackStatusChangeDto deviceTrackStatusChangeDto){
        deviceService.setTrackedStatus(deviceTrackStatusChangeDto);
        return  new ResponseEntity<>(HttpStatus.OK);
    }


}
