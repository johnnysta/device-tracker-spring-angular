package com.example.devicetracker.repository;

import com.example.devicetracker.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findAllByUserId(Long id);

    Optional<Device> findByImeiNumber(String imeiNumber);
}
