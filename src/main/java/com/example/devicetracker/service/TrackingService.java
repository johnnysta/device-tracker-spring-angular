package com.example.devicetracker.service;

import com.example.devicetracker.domain.Device;
import com.example.devicetracker.domain.Location;
import com.example.devicetracker.domain.TrackingData;
import com.example.devicetracker.dto.in.TrackingDataDto;
import com.example.devicetracker.dto.out.TrackingDataListItemDto;
import com.example.devicetracker.mapping.TrackingMapper;
import com.example.devicetracker.repository.TrackingRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
@AllArgsConstructor
public class TrackingService {

    private TrackingRepository trackingRepository;
    private DeviceService deviceService;
    private TrackingMapper trackingMapper;

    public void saveTrackingData(TrackingDataDto trackingDataDto) {
        Device device = deviceService.findDeviceByImeiNumber(trackingDataDto.getDeviceImeiNumber());
        Location location = new Location(trackingDataDto.getLatitude(), trackingDataDto.getLongitude());
        trackingRepository.save(trackingMapper.mapFromTrackingDataDtoToTrackingData(trackingDataDto, device, location));
    }

    public List<TrackingDataListItemDto> getTrackingDataByDeviceId(Long deviceId) {
        List<TrackingData> trackingDataList = trackingRepository.findAllByDeviceId(deviceId);
        return trackingDataList
                .stream()
                .map(trackingData -> trackingMapper.mapFromTrackingDataToTrackingDataListItemDto(trackingData))
                .collect(Collectors.toList());
    }
}
