package com.project.visit.service.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public record VisitInfoModel(Long id, String userId, String doctorName, String userName, Long time, String path,
                             String hourAndMin, boolean active) {

    public VisitInfoModel(Long id, Long time, boolean active) {
        this(id, null, null, null, time, null, getTime(time), active);
    }

    public static String getTime(Long time) {
        var a = LocalDateTime.ofEpochSecond(time, 0, ZoneOffset.UTC);
        return String.format("%s:%s", a.getHour(), a.getMinute());
    }

}
