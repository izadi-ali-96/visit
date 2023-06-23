package com.project.visit.resource.response;

import com.project.visit.resource.model.GenerateTimeModel;

import java.util.List;

public record GenerateTimeResponse(List<GenerateTimeModel> times) {
}
