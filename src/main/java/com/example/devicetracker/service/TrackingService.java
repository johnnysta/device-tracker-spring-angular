package com.example.devicetracker.service;

import com.example.devicetracker.domain.Device;
import com.example.devicetracker.domain.Location;
import com.example.devicetracker.dto.in.TrackingDataDto;
import com.example.devicetracker.mapping.TrackingMapper;
import com.example.devicetracker.repository.TrackingRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class TrackingService {

    private TrackingRepository trackingRepository;
    private DeviceService deviceService;
    private TrackingMapper trackingMapper;

    public void saveTrackingData(TrackingDataDto trackingDataDto) {
        Device device = deviceService.findDeviceByImeiNumber(trackingDataDto.getImeiNumber());
        Location location = new Location(trackingDataDto.getLatitude(), trackingDataDto.getLongitude());
        trackingRepository.save(trackingMapper.mapFromTrackingDataDtoToTrackingData(trackingDataDto, device, location));
    }
}
