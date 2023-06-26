package com.project.visit.resource.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignRequestModel {

	@NotNull
	private Long visitId;
}
