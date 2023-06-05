package com.project.visit.service;

import java.util.List;

import com.project.visit.model.Doctor;
import com.project.visit.service.model.AddressModel;
import com.project.visit.service.model.UserInfoModel;

public interface DoctorService {

	List<Doctor> findDoctorByCity(Long cityId);

	Doctor findDoctor(String code);

	void updateDoctorInfo(UserInfoModel model);

	void addAddress(AddressModel model);

	void updateAddress(Long addressId, AddressModel model);

	void deleteAddress(Long doctorId, Long addressIs);
}
