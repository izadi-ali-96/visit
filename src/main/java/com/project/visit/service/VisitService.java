package com.project.visit.service;

import java.util.List;

import com.project.visit.model.Visit;
import com.project.visit.service.model.GenerateVisitTimeInput;

public interface VisitService {

	List<Visit> generateVisitTimes(GenerateVisitTimeInput input);

	Visit assignVisit(Long visitId, String userId);

	void deleteVisit(Long visitId, String userId);

}
