package com.project.visit.service.model;

import com.project.visit.resource.model.TimePeriodModel;

public record TimeInput(int hour, int minute) {

    public TimeInput(TimePeriodModel model) {
        this(model.getHour(), model.getMinute());
    }
}
