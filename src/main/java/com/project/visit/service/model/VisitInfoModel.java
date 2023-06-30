package com.project.visit.service.model;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public record VisitInfoModel(Long id, String userId, String doctorName, String userName, Long time, String path,
                             String hourAndMin, boolean active) {

    public VisitInfoModel(Long id, Long time, boolean active) {
        this(id, null, null, null, time, null, getTime(time), active);
    }

    public static String getTime(Long time) {
        var a = ZonedDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.of(ZoneOffset.UTC.getId()));
        DecimalFormat formatter = new DecimalFormat("00");
        return String.format("%s:%s", formatter.format(a.getHour()), formatter.format(a.getMinute()));
    }

}
