package com.project.visit.resource;

import com.project.visit.resource.request.LogInRequest;
import com.project.visit.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationResource {

	private final AuthService service;

	@PostMapping("/login")
	ResponseEntity<String> login(@RequestBody LogInRequest request) {
		return ResponseEntity.ok(service.generateToken(request.getPhone(), request.getPassword()));
	}
}
