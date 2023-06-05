package com.project.visit.resource.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignRequestModel {

	@NotBlank
	private Long visitId;
}
