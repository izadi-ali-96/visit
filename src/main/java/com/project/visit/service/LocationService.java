package com.project.visit.service;

import java.util.List;

import com.project.visit.model.City;
import com.project.visit.model.Province;

public interface LocationService {

	List<Province> getAllProvince();

	List<City> getAllCity(Long provinceId);
}
