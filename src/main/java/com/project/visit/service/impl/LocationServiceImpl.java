package com.project.visit.service.impl;

import java.util.List;

import com.project.visit.model.City;
import com.project.visit.model.Province;
import com.project.visit.repository.CityRepository;
import com.project.visit.repository.ProvinceRepository;
import com.project.visit.service.LocationService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

	private final ProvinceRepository provinceRepository;

	private final CityRepository cityRepository;

	@Override
	public List<Province> getAllProvince() {
		return provinceRepository.findAll();
	}

	@Override
	public List<City> getAllCity(Long provinceId) {
		return cityRepository.findAllByProvinceId(provinceId);
	}
}
