package com.project.visit.service.model;

public record GenerateVisitTimeInput(Long from, Long to, String userId, Long addressId, Long index) {
}
