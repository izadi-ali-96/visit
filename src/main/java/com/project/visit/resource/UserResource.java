package com.project.visit.resource;

import com.project.visit.resource.mapper.UserResourceMapper;
import com.project.visit.resource.request.CreateDoctorRequestModel;
import com.project.visit.resource.request.CreateUserRequestModel;
import com.project.visit.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserResource {

	private final UserService service;

	private final UserResourceMapper mapper;

	@PostMapping("/patient")
	ResponseEntity<Void> createUser(@RequestBody CreateUserRequestModel model) {
		service.createUser(mapper.toUserCreationModel(model));
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/doctor")
	ResponseEntity<Void> createDoctor(@RequestBody CreateDoctorRequestModel model) {
		service.createDoctor(mapper.toUserCreationModel(model));
		return ResponseEntity.noContent().build();
	}
}
