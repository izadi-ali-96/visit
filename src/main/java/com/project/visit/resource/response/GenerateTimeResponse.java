package com.project.visit.resource.response;

import com.project.visit.resource.model.VisitLightModel;

import java.util.List;

public record GenerateTimeResponse(List<VisitLightModel> times, String day, String persianDay) {
}
