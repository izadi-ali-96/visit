package com.project.visit.service.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressModel {

	private String userId;

	private String title;

	private String path;

	private Long cityId;

	private List<String> phones;

	private List<String> days;

}
