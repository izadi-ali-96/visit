package com.project.visit.resource;

import com.project.visit.resource.filter.RequestContextInterceptor;
import com.project.visit.resource.mapper.DoctorResourceMapper;
import com.project.visit.resource.request.*;
import com.project.visit.resource.response.DoctorListResponseModel;
import com.project.visit.resource.response.DoctorResponseModel;
import com.project.visit.resource.response.DoctorStatusResponse;
import com.project.visit.resource.response.ExpertiseResponse;
import com.project.visit.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorResource {

    private final DoctorService service;

    private final DoctorResourceMapper mapper;

    @GetMapping
    ResponseEntity<DoctorListResponseModel> getDoctors(
            @RequestParam(name = "city", defaultValue = "1") Long cityId, @RequestParam(name = "tags", defaultValue = "", required = false) List<Long> tags) {
        var result = service.findDoctorByCity(cityId, tags).stream().map(mapper::toLightDoctorInfo).toList();
        return ResponseEntity.ok(new DoctorListResponseModel(result));
    }

    @PostMapping("/profile/description")
    ResponseEntity<Void> upsertDescription(@RequestBody UpsertDescriptionRequest request) {
        service.setDescription(RequestContextInterceptor.getCurrentContext().getUserId(), request.getDescription());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile/status")
    ResponseEntity<DoctorStatusResponse> getDoctorStatus() {
        var context = RequestContextInterceptor.getCurrentContext();
        return ResponseEntity.ok(new DoctorStatusResponse(service.checkDoctorActivation(context.getUserId())));
    }


    @GetMapping("/medical-code/{medicalCode}")
    ResponseEntity<DoctorResponseModel> getDoctorByMedicalCode(@PathVariable("medicalCode") String code) {
        return ResponseEntity.ok(new DoctorResponseModel(mapper.toDoctorData(service.findDoctor(code))));
    }

    @GetMapping("/profile")
    ResponseEntity<DoctorResponseModel> getDoctorByUserId() {
        var context = RequestContextInterceptor.getCurrentContext();
        return ResponseEntity.ok(new DoctorResponseModel(mapper.toDoctorData(service.findDoctorByUserId(context.getUserId()))));
    }

    @GetMapping("/expertise")
    ResponseEntity<ExpertiseResponse> getExpertise() {
        var result = service.getExpertise().stream().map(e -> new ExpertiseResponse.ExpertiseInfo(e.getId(), e.getName())).toList();
        return ResponseEntity.ok(new ExpertiseResponse(result));
    }

    @PostMapping("/add/expertise")
    ResponseEntity<Void> addExpertise(@RequestBody ExpertiseRequest request) {
        var context = RequestContextInterceptor.getCurrentContext();
        service.addExpertise(context.getUserId(), request.getExpertiseId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/expertise")
    ResponseEntity<Void> deleteExpertise(@RequestBody ExpertiseRequest request) {
        var context = RequestContextInterceptor.getCurrentContext();
        service.deleteExpertise(context.getUserId(), request.getExpertiseId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/info")
    ResponseEntity<Void> updateDoctorInfo(@Valid @RequestBody UpdateUserInfoRequestModel request) {
        service.updateDoctorInfo(mapper.toUserInfoModel(request, RequestContextInterceptor.getCurrentContext().getUserId()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/address")
    ResponseEntity<Void> addAddress(@RequestBody AddressRequestModel model) {
        service.addAddress(mapper.toAddressModel(model, RequestContextInterceptor.getCurrentContext().getUserId()));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/address/{addressId}")
    ResponseEntity<Void> addAddress(@PathVariable("addressId") Long addressId, @RequestBody AddressRequestModel model) {
        service.updateAddress(addressId, mapper.toAddressModel(model, RequestContextInterceptor.getCurrentContext().getUserId()));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/address/{addressId}")
    ResponseEntity<Void> deleteAddress(@PathVariable("addressId") Long addressId) {
        var context = RequestContextInterceptor.getCurrentContext();
        service.deleteAddress(context.getUserId(), addressId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/image")
    ResponseEntity<Void> uploadImage(@RequestBody ImageRequest request) throws IOException {
        var context = RequestContextInterceptor.getCurrentContext();
        service.saveFile(request.getFile(), context.getUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/image/{medicalCode}")
    ResponseEntity<Resource> getImage(@PathVariable("medicalCode") String code) throws IOException {
        var result = service.getFile(code);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(result);
    }

}
