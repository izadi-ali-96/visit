package com.project.visit.resource.response;

import java.util.List;

import com.project.visit.resource.model.LocationData;


public record CityResponseModel(List<LocationData> cities) {
}
