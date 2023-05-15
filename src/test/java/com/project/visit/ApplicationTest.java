package com.project.visit;

import com.project.visit.repository.CityRepository;
import com.project.visit.repository.DoctorRepository;
import com.project.visit.repository.ProvinceRepository;
import com.project.visit.service.DoctorService;
import com.project.visit.service.model.GenerateVisitTimeInput;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

class ApplicationTest extends BaseTestIT {

	@Autowired
	CityRepository cityRepository;

	@Autowired
	ProvinceRepository provinceRepository;

	@Autowired
	DoctorRepository doctorRepository;

	@Autowired
	DoctorService service;

	@Test
	void test() {
	}
}
