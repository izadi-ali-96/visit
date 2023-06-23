package com.project.visit.service.model;

import com.github.eloyzone.jalalicalendar.DateConverter;
import com.github.eloyzone.jalalicalendar.JalaliDateFormatter;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
@Setter
public class TimeModel {

    private final static Long TIME_PERIOD = 86398L;

    public TimeModel(LocalDate time, DateConverter converter) {
        from = time.atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond();
        to = time.atStartOfDay().toInstant(ZoneOffset.UTC).getEpochSecond() + TIME_PERIOD;

        var jalaliDate = converter.gregorianToJalali(time.getYear(), time.getMonth(), time.getDayOfMonth());
        day = jalaliDate.getDayOfWeek().getStringInPersian();
        persianTime = jalaliDate.format(new JalaliDateFormatter("M dd", JalaliDateFormatter.FORMAT_IN_PERSIAN));
    }
    private String day;

    private String persianTime;

    private Long from;

    private Long to;
}
