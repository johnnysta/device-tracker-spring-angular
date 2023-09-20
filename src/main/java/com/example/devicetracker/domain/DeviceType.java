package com.example.devicetracker.domain;

public enum DeviceType {

    MOBILE ("Mobile"),
    TABLET ("Tablet"),
    LAPTOP ("Laptop"),
    WATCH ("Watch"),
    OTHER ("Other");


    private String displayName;


    DeviceType(String displayName) {
        this.displayName = displayName;
    }
}
