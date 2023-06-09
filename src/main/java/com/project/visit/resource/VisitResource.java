package com.project.visit.resource;

import com.project.visit.resource.filter.RequestContextInterceptor;
import com.project.visit.resource.mapper.VisitResourceMapper;
import com.project.visit.resource.request.AssignRequestModel;
import com.project.visit.resource.request.DoctorVisitInfoRequest;
import com.project.visit.resource.request.GenerateVisitRequestMode;
import com.project.visit.resource.response.AssignVisitResponse;
import com.project.visit.resource.response.DoctorVisitInfoResponse;
import com.project.visit.resource.response.UserVisitInfoResponse;
import com.project.visit.resource.response.VisitResponseModel;
import com.project.visit.service.VisitService;
import com.project.visit.service.model.GenerateVisitTimeInput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visit")
@RequiredArgsConstructor
public class VisitResource {

    private final VisitService service;

    private final VisitResourceMapper mapper;

    @PostMapping("/generate")
    ResponseEntity<VisitResponseModel> generateVisits(@RequestBody GenerateVisitRequestMode request) {
        var context = RequestContextInterceptor.getCurrentContext();
        var req = new GenerateVisitTimeInput(request.from(), request.to(), context.getUserId(), request.addressId());
        var result = service.generateVisitTimes(req);
        return ResponseEntity.ok(new VisitResponseModel(result));
    }

    @PostMapping("/assign")
    ResponseEntity<AssignVisitResponse> assign(@Valid @RequestBody AssignRequestModel model) {
        var result = service.assignVisit(model.getVisitId(), RequestContextInterceptor.getCurrentContext().getUserId());
        return ResponseEntity.ok(new AssignVisitResponse(result.getId()));
    }

    @GetMapping("/user")
    ResponseEntity<UserVisitInfoResponse> getUserVisit() {
        var context = RequestContextInterceptor.getCurrentContext();
        var result = service.getUserVisits(context.getUserId());
        return ResponseEntity.ok(mapper.toUserVisitInfoResponse(result));
    }

    @GetMapping("/doctor")
    ResponseEntity<DoctorVisitInfoResponse> getUserVisit(@RequestBody DoctorVisitInfoRequest request) {
        var context = RequestContextInterceptor.getCurrentContext();
        var result = service.getDoctorVisit(context.getUserId(), request.getFrom(), request.getTo(), request.getAddressId());
        return ResponseEntity.ok(mapper.toDoctorVisitInfoResponse(result));
    }

}
