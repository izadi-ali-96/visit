package com.project.visit.service;

import com.project.visit.service.model.UserCreationModel;

public interface UserService {
	String createUser(UserCreationModel model);

	String createDoctor(UserCreationModel model);
}
