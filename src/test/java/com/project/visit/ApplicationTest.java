package com.project.visit;

import com.project.visit.repository.CityRepository;
import com.project.visit.repository.DoctorRepository;
import com.project.visit.repository.ProvinceRepository;
import com.project.visit.service.AuthService;
import com.project.visit.service.DoctorService;
import com.project.visit.service.VisitService;
import com.project.visit.service.model.GenerateVisitByHourInput;
import com.project.visit.service.model.TimeInput;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

//@Disabled
class ApplicationTest extends BaseTestIT {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    ProvinceRepository provinceRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    DoctorService service;

    @Autowired
    AuthService authService;

    @Autowired
    VisitService visitService;

    @Test
    void test() {
//        visitService.generateVisitTimes(new GenerateVisitByHourInput(new TimeInput(12, 30), null, null, 0L, null));
    }
}
