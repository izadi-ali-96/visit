package com.project.visit.service.impl;

import com.project.visit.exception.DoctorException;
import com.project.visit.exception.ExpertiseException;
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
import java.util.stream.Collectors;

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
    public Doctor findDoctorByUserId(String code) {
        return doctorRepository.findByUserId(code)
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

        checkDoctorActivation(doctor);
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
        var optionalDoctor = doctorRepository.findById(doctorId);
        optionalDoctor.ifPresentOrElse(doctor -> doctor.getAddresses()
                .stream()
                .filter(a -> a.getId() == addressIs).findFirst()
                .ifPresentOrElse(addressRepository::delete, () -> {
                    throw new DoctorException(ResponseResult.ADDRESS_NOT_FOUND);
                }), () -> {
            throw new DoctorException(ResponseResult.DOCTOR_NOT_FOUND);
        });
        checkDoctorActivation(optionalDoctor.get().getUserId());
    }

    @Override
    public List<Expertise> getExpertise() {
        return expertiseRepository.findAll();
    }

    @Override
    public void addExpertise(String userId, Long expertiseId) {
        var doctor = doctorRepository.findByUserId(userId).orElseThrow(() -> new DoctorException(ResponseResult.DOCTOR_NOT_FOUND));
        var ex = expertiseRepository.findById(expertiseId).orElseThrow(() -> new ExpertiseException(ResponseResult.EXPERTISE_NOT_FOUND));
        doctor.getExpertise().add(ex);
        doctor = doctorRepository.save(doctor);
        checkDoctorActivation(doctor);
    }

    @Override
    public void deleteExpertise(String userId, Long expertiseId) {
        var doctor = doctorRepository.findByUserId(userId).orElseThrow(() -> new DoctorException(ResponseResult.DOCTOR_NOT_FOUND));
        var ex = expertiseRepository.findById(expertiseId).orElseThrow(() -> new ExpertiseException(ResponseResult.EXPERTISE_NOT_FOUND));

        var list = doctor.getExpertise().stream().filter(e -> e.getId() != ex.getId()).collect(Collectors.toSet());
        doctor.setExpertise(list);

        doctor = doctorRepository.save(doctor);
        checkDoctorActivation(doctor);
    }

    @Override
    public void saveFile(MultipartFile file, String userId) throws IOException {
        var doctor = doctorRepository.findByUserId(userId).orElseThrow(IllegalStateException::new);
        var content = file.getBytes();
        FileOutputStream outputStream = new FileOutputStream(basePath + doctor.getMedicalCode(), true);
        outputStream.write(content);
        outputStream.close();

        doctor.setPictureUrl(String.format("http://185.126.200.26:8899/doctor/image/%s", doctor.getMedicalCode()));
        doctorRepository.save(doctor);
    }

    @Override
    public ByteArrayResource getFile(String medicalCode) throws IOException {
        var path = Paths.get(basePath + medicalCode);
        return new ByteArrayResource(Files.readAllBytes(path));
    }

    @Override
    public void setDescription(String userId, String description) {
        var doctor = doctorRepository.findByUserId(userId).orElseThrow(() -> new DoctorException(ResponseResult.DOCTOR_NOT_FOUND));
        doctor.setDescription(description);
        doctor = doctorRepository.save(doctor);
        checkDoctorActivation(doctor);
    }

    @Override
    public boolean checkDoctorActivation(String userId) {
        var doctor = doctorRepository.findByUserId(userId)
                .orElseThrow(() -> new DoctorException(ResponseResult.DOCTOR_NOT_FOUND));
        return checkDoctorActivation(doctor);
    }

    @Override
    public boolean checkDoctorActivation(Doctor doctor) {
        if (StringUtils.isNotBlank(doctor.getDescription()) && doctor.getAddresses().size() >= 1 && doctor.getExpertise().size() >= 1) {
            doctor.setActive(true);
            doctorRepository.save(doctor);
        } else {
            doctor.setActive(false);
            doctorRepository.save(doctor);
        }
        return doctor.isActive();

    }
}
