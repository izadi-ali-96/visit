package com.project.visit.resource;

import com.project.visit.resource.filter.RequestContextInterceptor;
import com.project.visit.resource.mapper.VisitResourceMapper;
import com.project.visit.resource.request.GenerateVisitRequestMode;
import com.project.visit.resource.response.VisitResponseModel;
import com.project.visit.service.VisitService;
import com.project.visit.service.model.GenerateVisitTimeInput;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visit")
@RequiredArgsConstructor
public class VisitResource {

	private final VisitService service;

	private final VisitResourceMapper mapper;

	@PostMapping("/generate")
	ResponseEntity<VisitResponseModel> generateVisits(@RequestBody GenerateVisitRequestMode request) {
		var context = RequestContextInterceptor.getCurrentContext();
		var req = new GenerateVisitTimeInput(request.from(), request.to(), context.getUserId());
		var result = service.generateVisitTimes(req);
		return ResponseEntity.ok(new VisitResponseModel(result));
	}

}
