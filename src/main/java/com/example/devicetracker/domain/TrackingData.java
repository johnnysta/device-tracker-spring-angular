package com.example.devicetracker.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class TrackingData {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "device_id")
    @ManyToOne
    private Device device;

    @JoinColumn(name = "location_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Location location;

    @Column(name = "date_time")
    LocalDateTime dateTime;


}
