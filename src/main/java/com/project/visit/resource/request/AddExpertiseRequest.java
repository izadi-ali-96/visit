package com.project.visit.resource.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddExpertiseRequest {

    private List<Long> expertise;
}
