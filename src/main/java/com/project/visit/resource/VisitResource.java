package com.project.visit.resource;

import com.project.visit.resource.mapper.VisitResourceMapper;
import com.project.visit.service.VisitService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visit")
@RequiredArgsConstructor
public class VisitResource {

	private final VisitService service;

	private final VisitResourceMapper mapper;

//	@PostMapping("/generate")
//	ResponseEntity<VisitResponseModel> generateVisits(@RequestBody GenerateVisitRequestMode request) {
//		var context = RequestContextInterceptor.getCurrentContext();
//		var req = new GenerateVisitTimeInput()
//		return ResponseEntity.ok(mapper.toVisitModel(service.generateVisitTimes()))
//	}

}
