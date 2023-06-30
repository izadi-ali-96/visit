package com.project.visit.service.impl;

import com.github.eloyzone.jalalicalendar.DateConverter;
import com.github.eloyzone.jalalicalendar.JalaliDateFormatter;
import com.project.visit.exception.DoctorException;
import com.project.visit.exception.ResponseResult;
import com.project.visit.exception.UserException;
import com.project.visit.exception.VisitException;
import com.project.visit.model.Visit;
import com.project.visit.repository.AddressRepository;
import com.project.visit.repository.UserRepository;
import com.project.visit.repository.VisitRepository;
import com.project.visit.service.VisitService;
import com.project.visit.service.mapper.VisitServiceMapper;
import com.project.visit.service.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    private final VisitServiceMapper mapper;

    private static final int PERIOD = 1200000;

    @Override
    public List<Visit> generateVisitTimes(GenerateVisitTimeInput input) {

        var converter = new DateConverter();
        var l = LocalDate.now().plus(input.index(), ChronoUnit.DAYS);
        var model = new TimeModel(l, converter);

        var address = addressRepository.findByIdAndDoctorUserId(input.addressId(), input.userId()).orElseThrow(() -> new DoctorException(ResponseResult.DOCTOR_NOT_FOUND));
        if (!address.getDays().contains(model.getDay())) {
            throw new VisitException(ResponseResult.VISIT_INVALID_TIME);
        }

        var visit = new ArrayList<Visit>();
        var visits = visitRepository.findAllByDoctorUserIdAndAddressIdAndTimeBetween(address.getDoctor().getUserId(), address.getId(), input.from(), input.to());
        if (!visits.isEmpty()) {
            throw new VisitException(ResponseResult.VISIT_EXIST_IN_THIS_TIME);
        }


        var start = input.from();
        var end = input.to();
        while (start + PERIOD <= end) {
            var v = new Visit();
            v.setDoctor(address.getDoctor());
            v.setTime(start);
            v.setAddress(address);
            visit.add(v);
            start = start + PERIOD;
        }
        return visitRepository.saveAll(visit);
    }

    @Override
    public List<Visit> generateVisitTimes(GenerateVisitByHourInput input) {
        var address = addressRepository.findByIdAndDoctorUserId(input.addressId(), input.userId()).orElseThrow(() -> new DoctorException(ResponseResult.ADDRESS_NOT_FOUND));

        var localDate = LocalDate.now().plus(input.index(), ChronoUnit.DAYS);

        var start = LocalDateTime.of(localDate, LocalTime.of(input.from().hour(), input.from().minute())).toEpochSecond(ZoneOffset.UTC) * 1000;
        var end = LocalDateTime.of(localDate, LocalTime.of(input.to().hour(), input.to().minute())).toEpochSecond(ZoneOffset.UTC) * 1000;

        var visits = visitRepository.findAllByDoctorUserIdAndAddressIdAndTimeBetween(address.getDoctor().getUserId(), address.getId(), start, end);
        if (!visits.isEmpty()) {
            throw new VisitException(ResponseResult.VISIT_EXIST_IN_THIS_TIME);
        }

        var visit = new ArrayList<Visit>();
        while (start + PERIOD <= end) {
            var v = new Visit();
            v.setDoctor(address.getDoctor());
            v.setTime(start);
            v.setAddress(address);
            visit.add(v);
            start = start + PERIOD;
        }
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
        var visit = visitRepository.findByIdAndDoctorUserId(visitId, userId).orElseThrow(() -> new VisitException(ResponseResult.VISIT_NOT_FOUND));
        visitRepository.delete(visit);
    }

    @Override
    public void unAssignVisit(Long visitId, String userId) {
        visitRepository.findByIdAndUserUserId(visitId, userId).ifPresent(v -> {
            v.setUser(null);
            visitRepository.save(v);
        });
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

    @Override
    public List<VisitInfoModel> getVisitOfDoctor(Long from, Long to, Long addressId) {
        var result = visitRepository.findAllByAddressIdAndTimeBetween(addressId, from, to);
        return mapper.toVisitLightModel(result);
    }

    @Override
    public CurrentTimeServiceModel getCurrentTime(Long index) {
        var converter = new DateConverter();
        var time = LocalDate.now().plus(index, ChronoUnit.DAYS);

        var jalaliDate = converter.gregorianToJalali(time.getYear(), time.getMonth(), time.getDayOfMonth());
        var day = jalaliDate.getDayOfWeek().getStringInPersian();
        return new CurrentTimeServiceModel(jalaliDate.getDayOfWeek().getStringInPersian(), jalaliDate.getDayOfWeek().getValue(),
                jalaliDate.getMonthPersian().getStringInPersian(), jalaliDate.getMonthPersian().getValue(), jalaliDate.getYear(),
                day + " " + jalaliDate.format(new JalaliDateFormatter("M dd", JalaliDateFormatter.FORMAT_IN_PERSIAN)));
    }

}
