package com.project.visit.exception;

import lombok.ToString;

@ToString(callSuper = true)

public class VisitException extends BusinessException {

    public VisitException(ResponseResult result) {
        super(result);
    }
}
