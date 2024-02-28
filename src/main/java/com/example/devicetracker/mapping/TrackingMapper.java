package com.example.devicetracker.mapping;

import com.example.devicetracker.domain.Device;
import com.example.devicetracker.domain.Location;
import com.example.devicetracker.domain.TrackingData;
import com.example.devicetracker.dto.in.TrackingDataDto;
import com.example.devicetracker.dto.out.TrackingDataListItemDto;
import org.springframework.stereotype.Component;

@Component
public class TrackingMapper {

    public TrackingData mapFromTrackingDataDtoToTrackingData(TrackingDataDto trackingDataDto, Device device, Location location) {
        TrackingData trackingData = new TrackingData();
        trackingData.setDateTime(trackingDataDto.getDateTime());
        trackingData.setLocation(location);
        trackingData.setDevice(device);
        return trackingData;
    }

    public TrackingDataListItemDto mapFromTrackingDataToTrackingDataListItemDto(TrackingData trackingData) {
        TrackingDataListItemDto trackingDataListItemDto = new TrackingDataListItemDto();
        trackingDataListItemDto.setLongitude(trackingData.getLocation().getLongitude());
        trackingDataListItemDto.setLatitude(trackingData.getLocation().getLatitude());
        trackingDataListItemDto.setDateTime(trackingData.getDateTime().toString());
        trackingDataListItemDto.setDeviceId(trackingData.getDevice().getId());
        trackingDataListItemDto.setDeviceName(trackingData.getDevice().getDeviceName());
        return trackingDataListItemDto;
    }
}
