package com.example.devicetracker.controller;


import com.example.devicetracker.dto.in.TrackingDataDto;
import com.example.devicetracker.service.TrackingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tracking")
@Slf4j

public class TrackingController {

    private TrackingService trackingService;

    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }


    @PostMapping
    public ResponseEntity<Void> saveTrackingData(@RequestBody TrackingDataDto trackingData) {
        trackingService.saveTrackingData(trackingData);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
