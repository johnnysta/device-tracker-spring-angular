package com.example.devicetracker.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="device_name")
    private String deviceName;
    @Column(name="imei_number", unique = true)
    private String imeiNumber;
    @Column(name="is_tracked")
    private Boolean isTracked;
    @ManyToOne
    private Account user;
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = UsageType.class, fetch=FetchType.EAGER)
    private List<UsageType> usageTypeList = new ArrayList<>();


}
