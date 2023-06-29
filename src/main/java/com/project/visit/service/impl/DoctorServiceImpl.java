package com.project.visit.service.impl;

import com.project.visit.exception.DoctorException;
import com.project.visit.exception.ResponseResult;
import com.project.visit.model.Address;
import com.project.visit.model.Doctor;
import com.project.visit.model.Expertise;
import com.project.visit.repository.*;
import com.project.visit.service.DoctorService;
import com.project.visit.service.model.AddressModel;
import com.project.visit.service.model.UserInfoModel;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    private final CityRepository cityRepository;

    private final ExpertiseRepository expertiseRepository;

    private final static String basePath = "/home/project/pic/";

//    private final static String basePath = "/Users/ali/Desktop/test/";


    public List<Doctor> findDoctorByCity(Long cityId, List<Long> tags) {
        return doctorRepository.findDoctorsByCityId(cityId, tags);
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

    @Override
    public List<Expertise> getExpertise() {
        return expertiseRepository.findAll();
    }

    @Override
    public void setExpertise(String userId, List<Long> expertise) {
        doctorRepository.findByUserId(userId).ifPresent(doctor -> {
            doctor.setExpertise(expertiseRepository.findAllByIdIn(expertise));
        });
    }

    @Override
    public void saveFile(MultipartFile file, String userId) throws IOException {
        var doctor = doctorRepository.findByUserId(userId).orElseThrow(IllegalStateException::new);
        var content = file.getBytes();
        FileOutputStream outputStream = new FileOutputStream(basePath + doctor.getMedicalCode(), true);
        outputStream.write(content);
        outputStream.close();

        doctor.setPictureUrl(String.format("http://37.152.176.176:8899/doctor/image/%s", doctor.getMedicalCode()));
        doctorRepository.save(doctor);
    }

    @Override
    public ByteArrayResource getFile(String medicalCode) throws IOException {
        var path = Paths.get(basePath + medicalCode);
        return new ByteArrayResource(Files.readAllBytes(path));
    }
}
