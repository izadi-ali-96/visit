package com.project.visit.resource.request;

import com.project.visit.resource.model.TimePeriodModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateVisitByHourRequest {

    private TimePeriodModel from;

    private TimePeriodModel to;

    private Long addressId;
}
