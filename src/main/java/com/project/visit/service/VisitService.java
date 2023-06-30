package com.project.visit.service;

import com.project.visit.model.Visit;
import com.project.visit.service.model.CurrentTimeServiceModel;
import com.project.visit.service.model.GenerateVisitByHourInput;
import com.project.visit.service.model.GenerateVisitTimeInput;
import com.project.visit.service.model.VisitInfoModel;

import java.util.List;

public interface VisitService {

    List<Visit> generateVisitTimes(GenerateVisitTimeInput input);

    List<Visit> generateVisitTimes(GenerateVisitByHourInput input);
    Visit assignVisit(Long visitId, String userId);

    void deleteVisit(Long visitId, String userId);

    void unAssignVisit(Long visitId, String userId);

    List<VisitInfoModel> getUserVisits(String userId);

    List<VisitInfoModel> getDoctorVisit(String doctorId, Long from, Long to, Long addressId);

    List<VisitInfoModel> getVisitOfDoctor(Long from, Long to, Long addressId);

    CurrentTimeServiceModel getCurrentTime(Long index);

}
