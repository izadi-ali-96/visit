package com.project.visit.resource.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequestModel {

	@NotBlank
	private String phone;

	@NotBlank
	private String password;

	@NotBlank
	private String name;

	@NotBlank
	private String family;

}
