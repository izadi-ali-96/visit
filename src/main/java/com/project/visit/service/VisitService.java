package com.project.visit.service;

import com.project.visit.model.Visit;
import com.project.visit.service.model.GenerateVisitTimeInput;
import com.project.visit.service.model.VisitModel;

import java.util.List;

public interface VisitService {

    List<Visit> generateVisitTimes(GenerateVisitTimeInput input);

    Visit assignVisit(Long visitId, String userId);

    void deleteVisit(Long visitId, String userId);

    List<VisitModel> getUserVisits(String userId);

    List<VisitModel> getDoctorVisit(String doctorId, Long from, Long to, Long addressId);

}
