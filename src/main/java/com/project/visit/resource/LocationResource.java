package com.project.visit.resource;

import com.project.visit.resource.model.LocationData;
import com.project.visit.resource.response.CityResponseModel;
import com.project.visit.resource.response.ProvinceResponseModel;
import com.project.visit.service.LocationService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationResource {

	private final LocationService service;

	@GetMapping("/province")
	ResponseEntity<ProvinceResponseModel> getProvince() {
		var location = service.getAllProvince().stream().map(p -> new LocationData(p.getId(), p.getName())).toList();
		return ResponseEntity.ok(new ProvinceResponseModel(location));
	}

	@GetMapping("/{provinceId}/city")
	ResponseEntity<CityResponseModel> getCitiesOfProvince(@PathVariable Long provinceId) {
		var location = service.getAllCity(provinceId).stream().map(c -> new LocationData(c.getId(), c.getName())).toList();
		return ResponseEntity.ok(new CityResponseModel(location));
	}

}
