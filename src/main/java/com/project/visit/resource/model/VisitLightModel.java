package com.project.visit.resource.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class VisitLightModel {

    Long id;

    Long time;

    String userId;

    boolean active;
}
