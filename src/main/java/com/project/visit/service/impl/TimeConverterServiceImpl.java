package com.project.visit.service.impl;

import com.github.eloyzone.jalalicalendar.DateConverter;
import com.project.visit.service.TimeConverterService;
import com.project.visit.service.VisitService;
import com.project.visit.service.model.TimeAndVisitInfoModel;
import com.project.visit.service.model.TimeModel;
import com.project.visit.service.model.VisitInfoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class TimeConverterServiceImpl implements TimeConverterService {

    private final VisitService service;

    @Override
    public TimeAndVisitInfoModel getTime(Long addressId, String time, long index) {
        var converter = new DateConverter();
        var l = LocalDate.now().plus(index, ChronoUnit.DAYS);
        var model = new TimeModel(l, converter);

        var result = service.getVisitOfDoctor(model.getFrom(), model.getTo(), addressId);
        result = result.stream().sorted(Comparator.comparingLong(VisitInfoModel::time)).toList();
        return new TimeAndVisitInfoModel(result, model);
    }
}
