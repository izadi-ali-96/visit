package com.project.visit.exception;

import lombok.ToString;

@ToString(callSuper = true)

public class DoctorException extends BusinessException {

	public DoctorException(ResponseResult result) {
		super(result);
	}
}
