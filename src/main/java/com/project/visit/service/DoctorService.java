package com.project.visit.service;

import com.project.visit.model.Doctor;
import com.project.visit.model.Expertise;
import com.project.visit.service.model.AddressModel;
import com.project.visit.service.model.UserInfoModel;

import java.util.List;

public interface DoctorService {

    List<Doctor> findDoctorByCity(Long cityId, List<Long> tags);

    Doctor findDoctor(String code);

    void updateDoctorInfo(UserInfoModel model);

    void addAddress(AddressModel model);

    void updateAddress(Long addressId, AddressModel model);

    void deleteAddress(Long doctorId, Long addressIs);

    List<Expertise> getExpertise();
}
