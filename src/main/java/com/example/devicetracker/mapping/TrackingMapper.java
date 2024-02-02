package com.example.devicetracker.mapping;

import com.example.devicetracker.domain.Device;
import com.example.devicetracker.domain.Location;
import com.example.devicetracker.domain.TrackingData;
import com.example.devicetracker.dto.in.TrackingDataDto;
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
}
