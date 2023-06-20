package com.project.visit.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public abstract class BusinessException extends RuntimeException {

    private final ResponseResult result;

    BusinessException(ResponseResult result) {
        this.result = result;
    }
}
