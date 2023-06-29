package com.project.visit.service;

import com.project.visit.model.Doctor;
import com.project.visit.model.Expertise;
import com.project.visit.service.model.AddressModel;
import com.project.visit.service.model.UserInfoModel;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public interface DoctorService {

    List<Doctor> findDoctorByCity(Long cityId, List<Long> tags);

    Doctor findDoctor(String code);

    void updateDoctorInfo(UserInfoModel model);

    void addAddress(AddressModel model);

    void updateAddress(Long addressId, AddressModel model);

    void deleteAddress(Long doctorId, Long addressIs);

    List<Expertise> getExpertise();

    void setExpertise(String userId, List<Long> expertise);
    void saveFile(MultipartFile file, String userId) throws IOException;

    ByteArrayResource getFile(String medicalCode) throws IOException;
}
