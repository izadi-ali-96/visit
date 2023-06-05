package com.project.visit.resource.mapper;

import com.project.visit.resource.request.CreateDoctorRequestModel;
import com.project.visit.resource.request.CreateUserRequestModel;
import com.project.visit.service.model.UserCreationModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserResourceMapper {

	UserCreationModel toUserCreationModel(CreateUserRequestModel model);

	UserCreationModel toUserCreationModel(CreateDoctorRequestModel model);

}
