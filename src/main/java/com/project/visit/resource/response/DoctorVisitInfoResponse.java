package com.project.visit.resource.response;


import com.project.visit.resource.model.DoctorVisitInfo;

import java.util.List;

public record DoctorVisitInfoResponse(List<DoctorVisitInfo> infos) {
}
