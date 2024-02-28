package com.example.devicetracker.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class TrackingSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "metering_frequency")
    private Integer meteringFrequency = 1;

    @JoinColumn(name = "_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Location geofenceCenter;

    @Column(name = "geofence_radius")
    private Integer geofenceRadius;


}
