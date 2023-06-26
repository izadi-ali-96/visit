package com.project.visit.service;

import com.project.visit.service.model.TimeAndVisitInfoModel;

import java.util.List;

public interface TimeConverterService {

    TimeAndVisitInfoModel getTime(Long addressId, String time, long index);

}
