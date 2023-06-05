package com.project.visit.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.project.visit.exception.ResponseResult;
import com.project.visit.exception.UserException;
import com.project.visit.exception.VisitException;
import com.project.visit.model.Visit;
import com.project.visit.repository.DoctorRepository;
import com.project.visit.repository.UserRepository;
import com.project.visit.repository.VisitRepository;
import com.project.visit.service.VisitService;
import com.project.visit.service.model.GenerateVisitTimeInput;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

	private final VisitRepository visitRepository;

	private final DoctorRepository doctorRepository;

	private final UserRepository userRepository;

	private static final int PERIOD = 20 * 60 * 1000;

	@Override
	public List<Visit> generateVisitTimes(GenerateVisitTimeInput input) {
		var visit = new ArrayList<Visit>();
		doctorRepository.findByMedicalCode(input.medicalCode()).ifPresent(doctor -> {
			var start = input.from();
			while (start + PERIOD <= input.to()) {
				var v = new Visit();
				v.setDoctor(doctor);
				v.setTime(start);
				visit.add(v);
				start = start + PERIOD;
			}
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

}
