package com.project.visit.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseResult {

	DOCTOR_NOT_FOUND("100", "doctor.not.found"),
	ADDRESS_NOT_FOUND("101", "address.not.found"),
	INVALID_ACCESS("1000", "invalid.access"),
	VISIT_NOT_FOUND("2000", "visit.not.found"),
	VISIT_ASSIGNED("2001", "visit.assigned"),

	VISIT_NOT_BELONG_TO_USER("2002", "visit.not.belong.to.user"),

	USER_NOT_FOUND("3000", "user.not.found");

	private final String code;

	private final String message;
}
