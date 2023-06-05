package com.project.visit.service;

import com.project.visit.service.model.AuthModel;

public interface AuthService {

	String generateToken(String phone, String password);

	AuthModel checkToken(String token);
}
