package com.project.visit.resource;

import com.project.visit.resource.filter.RequestContextInterceptor;
import com.project.visit.resource.mapper.DoctorResourceMapper;
import com.project.visit.resource.request.AddressRequestModel;
import com.project.visit.resource.request.UpdateUserInfoRequestModel;
import com.project.visit.resource.response.DoctorListResponseModel;
import com.project.visit.resource.response.DoctorResponseModel;
import com.project.visit.resource.response.ExpertiseResponse;
import com.project.visit.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/{medicalCode}")
    ResponseEntity<DoctorResponseModel> getDoctor(@PathVariable("medicalCode") String code) {
        return ResponseEntity.ok(new DoctorResponseModel(mapper.toDoctorData(service.findDoctor(code))));
    }

    @GetMapping("/expertise")
    ResponseEntity<ExpertiseResponse> getExpertise() {
        var result = service.getExpertise().stream().map(e -> new ExpertiseResponse.ExpertiseInfo(e.getId(), e.getName())).toList();
        return ResponseEntity.ok(new ExpertiseResponse(result));
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
        service.deleteAddress(Long.valueOf(context.getUserId()), addressId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/image")
    ResponseEntity<Void> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        var context = RequestContextInterceptor.getCurrentContext();
        service.saveFile(file, context.getUserId());
        return ResponseEntity.ok().build();
    }

}
