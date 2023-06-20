package com.project.visit.exception;

import lombok.ToString;

@ToString(callSuper = true)
public class AuthException extends BusinessException {

    public AuthException(ResponseResult result) {
        super(result);
    }
}
