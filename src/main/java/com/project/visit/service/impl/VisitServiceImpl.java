package com.project.visit.service.impl;

import com.project.visit.exception.ResponseResult;
import com.project.visit.exception.UserException;
import com.project.visit.exception.VisitException;
import com.project.visit.model.Visit;
import com.project.visit.repository.AddressRepository;
import com.project.visit.repository.DoctorRepository;
import com.project.visit.repository.UserRepository;
import com.project.visit.repository.VisitRepository;
import com.project.visit.service.VisitService;
import com.project.visit.service.mapper.VisitServiceMapper;
import com.project.visit.service.model.GenerateVisitTimeInput;
import com.project.visit.service.model.VisitInfoModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    private final DoctorRepository doctorRepository;

    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    private final VisitServiceMapper mapper;

    private static final int PERIOD = 20 * 60 * 1000;

    @Override
    public List<Visit> generateVisitTimes(GenerateVisitTimeInput input) {
        var visit = new ArrayList<Visit>();
        addressRepository.findByIdAndDoctorUserId(input.addressId(), input.userId()).ifPresent((address) -> {
            doctorRepository.findByUserId(input.userId()).ifPresent(doctor -> {
                var start = input.from();
                while (start + PERIOD <= input.to()) {
                    var v = new Visit();
                    v.setDoctor(doctor);
                    v.setTime(start);
                    v.setAddress(address);
                    visit.add(v);
                    start = start + PERIOD;
                }
            });

        });
        return visitRepository.saveAll(visit);
    }

    @Override
    public Visit assignVisit(Long visitId, String userId) {
        var visit = visitRepository.findById(visitId).orElseThrow(() -> new VisitException(ResponseResult.VISIT_NOT_FOUND));
        if (visit.getUser() != null) {
            throw new VisitException(ResponseResult.VISIT_ASSIGNED);
        }
        var user = userRepository.findByUserId(userId).orElseThrow(() -> new UserException(ResponseResult.USER_NOT_FOUND));
        visit.setUser(user);
        return visitRepository.save(visit);
    }

    @Override
    public void deleteVisit(Long visitId, String userId) {
        var visit = visitRepository.findById(visitId).orElseThrow(() -> new VisitException(ResponseResult.VISIT_NOT_FOUND));
        if (!visit.getUser().getUserId().equals(userId)) {
            throw new VisitException(ResponseResult.VISIT_NOT_BELONG_TO_USER);
        }
        visit.setUser(null);
        visitRepository.save(visit);
    }
    @Override
    public List<VisitInfoModel> getUserVisits(String userId) {
        var result = visitRepository.findAllByUserUserId(userId);
        return mapper.toVisitModel(result);
    }
    @Override
    public List<VisitInfoModel> getDoctorVisit(String doctorId, Long from, Long to, Long addressId) {
        return mapper.toVisitModel(visitRepository.findAllByDoctorUserIdAndAddressIdAndTimeBetween(doctorId, addressId, from, to));
    }

}
