package com.project.visit.resource;

import com.project.visit.resource.filter.RequestContextInterceptor;
import com.project.visit.resource.mapper.DoctorResourceMapper;
import com.project.visit.resource.response.DoctorListResponseModel;
import com.project.visit.resource.response.DoctorResponseModel;
import com.project.visit.service.DoctorService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorResource {

	private final DoctorService service;

	private final DoctorResourceMapper mapper;

	@GetMapping
	ResponseEntity<DoctorListResponseModel> getDoctors(
			@RequestParam(name = "city", defaultValue = "1") Long cityId) {
		var result = service.findDoctorByCity(cityId).stream().map(mapper::toLightDoctorInfo).toList();
		return ResponseEntity.ok(new DoctorListResponseModel(result));
	}

	@GetMapping("/{medicalCode}")
	ResponseEntity<DoctorResponseModel> getDoctor(@PathVariable("medicalCode") String code) {
		return ResponseEntity.ok(new DoctorResponseModel(mapper.toDoctorData(service.findDoctor(code))));
	}

	@DeleteMapping("/address/{addressId}")
	ResponseEntity<Void> deleteAddress(@PathVariable("addressId") Long addressId) {
		var context = RequestContextInterceptor.getCurrentContext();
		service.deleteAddress(Long.valueOf(context.getUserId()), addressId);
		return ResponseEntity.ok().build();
	}

}
