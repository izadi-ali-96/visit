package com.project.visit.service.model;

public record GenerateVisitByHourInput(TimeInput from, TimeInput to, Long addressId, Long index, String userId) {
}
