package com.project.visit.service;

import java.util.List;

import com.project.visit.model.Doctor;

public interface DoctorService {

	List<Doctor> findDoctorByCity(Long cityId);

	Doctor findDoctor(String code);

	void deleteAddress(Long doctorId, Long addressIs);
}
