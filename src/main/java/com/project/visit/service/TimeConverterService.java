package com.project.visit.service;

import com.project.visit.service.model.TimeModel;

import java.util.List;

public interface TimeConverterService {

    List<TimeModel> getTime(String time, long index);

}
