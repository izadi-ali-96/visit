package com.project.visit.service.impl;

import java.util.List;

import com.project.visit.exception.DoctorException;
import com.project.visit.exception.ResponseResult;
import com.project.visit.model.Doctor;
import com.project.visit.repository.AddressRepository;
import com.project.visit.repository.DoctorRepository;
import com.project.visit.service.DoctorService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

	private final DoctorRepository doctorRepository;

	private final AddressRepository addressRepository;


	public List<Doctor> findDoctorByCity(Long cityId) {
		return doctorRepository.findDoctorsByCityId(cityId);
	}

	@Override
	public Doctor findDoctor(String code) {
		return doctorRepository.findByMedicalCode(code)
				.orElseThrow(() -> new DoctorException(ResponseResult.DOCTOR_NOT_FOUND));
	}

	@Override
	public void deleteAddress(Long doctorId, Long addressIs) {
		doctorRepository.findById(doctorId).ifPresentOrElse(doctor -> doctor.getAddresses()
				.stream()
				.filter(a -> a.getId() == addressIs).findFirst()
				.ifPresentOrElse(addressRepository::delete, () -> {
					throw new DoctorException(ResponseResult.ADDRESS_NOT_FOUND);
				}), () -> {
			throw new DoctorException(ResponseResult.DOCTOR_NOT_FOUND);
		});
	}
}
