package com.project.visit.resource.response;

import java.util.List;

import com.project.visit.model.Visit;

public record VisitResponseModel(List<Visit> visits) {
}
