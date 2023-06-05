package com.project.visit.service.impl;

import java.util.List;

import com.project.visit.exception.DoctorException;
import com.project.visit.exception.ResponseResult;
import com.project.visit.model.Address;
import com.project.visit.model.Doctor;
import com.project.visit.repository.AddressRepository;
import com.project.visit.repository.CityRepository;
import com.project.visit.repository.DoctorRepository;
import com.project.visit.repository.UserRepository;
import com.project.visit.service.DoctorService;
import com.project.visit.service.model.AddressModel;
import com.project.visit.service.model.UserInfoModel;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

	private final DoctorRepository doctorRepository;

	private final AddressRepository addressRepository;

	private final UserRepository userRepository;

	private final CityRepository cityRepository;

	public List<Doctor> findDoctorByCity(Long cityId) {
		return doctorRepository.findDoctorsByCityId(cityId);
	}

	@Override
	public Doctor findDoctor(String code) {
		return doctorRepository.findByMedicalCode(code)
				.orElseThrow(() -> new DoctorException(ResponseResult.DOCTOR_NOT_FOUND));
	}

	@Override
	@Transactional
	public void updateDoctorInfo(UserInfoModel model) {
		doctorRepository.findByUserId(model.getUserId()).ifPresent(doctor -> {
			if (StringUtils.isNotBlank(model.getName())) {
				doctor.setName(model.getName());
			}
			if (StringUtils.isNotBlank(model.getFamily())) {
				doctor.setName(model.getFamily());
			}
			doctorRepository.save(doctor);
		});

		if (!model.getPhone().isBlank() && model.getPhone() != null) {
			userRepository.findByUserId(model.getUserId()).ifPresent(user -> {
				user.setPhone(model.getPhone());
				userRepository.save(user);
			});
		}
	}

	@Override
	public void addAddress(AddressModel model) {
		var doctor = doctorRepository.findByUserId(model.getUserId())
				.orElseThrow(() -> new DoctorException(ResponseResult.DOCTOR_NOT_FOUND));
		var city = cityRepository.findById(model.getCityId())
				.orElseThrow(() -> new DoctorException(ResponseResult.CITY_NOT_FOUND));
		var address = new Address();
		address.setDoctor(doctor);
		address.setCity(city);
		address.setDays(model.getDays());
		address.setPath(model.getPath());
		address.setTitle(model.getTitle());
		address.setPhones(model.getPhones());
		addressRepository.save(address);
	}

	@Override
	public void updateAddress(Long addressId, AddressModel model) {
		var address = addressRepository.findById(addressId)
				.orElseThrow(() -> new DoctorException(ResponseResult.ADDRESS_NOT_FOUND));
		address.setDays(model.getDays());
		address.setPath(model.getPath());
		address.setTitle(model.getTitle());
		address.setPhones(model.getPhones());
		addressRepository.save(address);
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
