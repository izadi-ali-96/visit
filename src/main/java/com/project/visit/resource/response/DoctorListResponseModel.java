package com.project.visit.resource.response;

import java.util.List;

import com.project.visit.resource.model.DoctorData;

public record DoctorListResponseModel(List<DoctorData> doctors) {
}
