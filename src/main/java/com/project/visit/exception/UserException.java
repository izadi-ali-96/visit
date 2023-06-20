package com.project.visit.exception;

import lombok.ToString;

@ToString(callSuper = true)

public class UserException extends BusinessException {
	public UserException(ResponseResult result) {
		super(result);
	}
}
