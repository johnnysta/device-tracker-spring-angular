package com.example.devicetracker.controller;

import com.example.devicetracker.dto.outgoing.DeviceCreationInitDataDto;
import com.example.devicetracker.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/init-data")
    public ResponseEntity <DeviceCreationInitDataDto> getInitData(){
        DeviceCreationInitDataDto initData = deviceService.getInitData();
        return new ResponseEntity<>(initData, HttpStatus.OK);
    }

}
