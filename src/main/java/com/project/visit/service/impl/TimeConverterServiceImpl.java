package com.project.visit.service.impl;

import com.github.eloyzone.jalalicalendar.DateConverter;
import com.project.visit.service.TimeConverterService;
import com.project.visit.service.model.TimeModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeConverterServiceImpl implements TimeConverterService {

    @Override
    public List<TimeModel> getTime(String time, long index) {
        var converter = new DateConverter();
        List<TimeModel> models = new ArrayList<>();
        var localDate = LocalDateTime.parse(time).plus(index, ChronoUnit.WEEKS);
        for (int d = 0; d < 7; d++) {
            models.add(new TimeModel(localDate.toLocalDate(), converter));
            localDate = localDate.plusDays(1);
        }
        return models;
    }
}
