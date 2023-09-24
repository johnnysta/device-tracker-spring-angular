package com.example.devicetracker.domain;

public enum UsageType {

    WORK ("Work"),
    HOME ("Home"),
    HOBBY ("Hobby"),
    SPORT ("Sport"),
    OTHER ("Other");

    String displayName;

    UsageType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
