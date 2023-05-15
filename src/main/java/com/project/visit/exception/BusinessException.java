package com.project.visit.exception;

import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {

	private final ResponseResult result;

	BusinessException(ResponseResult result) {
		this.result = result;
	}
}
