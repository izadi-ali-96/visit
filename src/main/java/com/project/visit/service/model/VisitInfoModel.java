package com.project.visit.service.model;

public record VisitInfoModel(Long id, String userId, String doctorName, String userName, Long time, String path,
                             boolean active) {
    public VisitInfoModel(Long id, Long time, boolean active) {
        this(id, null, null, null, time, null, active);
    }
}
