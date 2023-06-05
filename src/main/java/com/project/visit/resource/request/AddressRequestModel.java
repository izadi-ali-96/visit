package com.project.visit.resource.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressRequestModel {

	@NotBlank
	private String title;

	@NotBlank
	private String path;

	@NotNull
	private Long cityId;

	private List<String> phones;

	private List<String> days;
}
