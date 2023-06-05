package com.project.visit;

import com.project.visit.repository.CityRepository;
import com.project.visit.repository.DoctorRepository;
import com.project.visit.repository.ProvinceRepository;
import com.project.visit.service.AuthService;
import com.project.visit.service.DoctorService;
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

	@Autowired
	AuthService authService;

	@Test
	void test() {
		authService.checkToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InRlc3QiLCJleHAiOjE2ODU5ODAyNDAsInJvbGUiOlsiVVNFUiIsIkRPQ1RPUiJdfQ.dr9sIpGRj87MB-gbfYCpdLE-4dGxXC_XQ1cZ7kl3Zaw");
	}
}
