package com.project.visit.model;

public record CurrentTimeModel(String day, String month, String year, String fullDate) {

    public CurrentTimeModel(String fullDate) {
        this(null, null, null, fullDate);
    }
}
