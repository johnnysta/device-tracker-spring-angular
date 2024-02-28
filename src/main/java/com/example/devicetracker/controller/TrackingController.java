package com.example.devicetracker.controller;


import com.example.devicetracker.dto.in.TrackingDataDto;
import com.example.devicetracker.dto.out.TrackingDataListItemDto;
import com.example.devicetracker.service.TrackingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracking")
@Slf4j
public class TrackingController {

    private final TrackingService trackingService;

    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }


    @PostMapping
    public ResponseEntity<Void> saveTrackingData(@RequestBody TrackingDataDto trackingData) {
        trackingService.saveTrackingData(trackingData);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<List<TrackingDataListItemDto>> getTrackingDataByDeviceId(@PathVariable Long deviceId) {
        List<TrackingDataListItemDto> resultList = trackingService.getTrackingDataByDeviceId(deviceId);
        log.info("resultlist length: " + resultList.size());
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }


}
