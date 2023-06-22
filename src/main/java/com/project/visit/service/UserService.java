package com.project.visit.service;

import com.project.visit.service.model.UserCreationModel;

public interface UserService {
	void createUser(UserCreationModel model);

	void createDoctor(UserCreationModel model);
}
