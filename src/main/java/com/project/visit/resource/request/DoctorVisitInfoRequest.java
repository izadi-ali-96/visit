package com.project.visit.resource.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorVisitInfoRequest {

    private Long from;

    private Long to;

    private Long addressId;
}
