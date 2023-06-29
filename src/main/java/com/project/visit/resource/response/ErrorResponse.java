package com.project.visit.resource.response;

import com.project.visit.exception.ResponseResult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    public ErrorResponse(ResponseResult result) {
        code = result.getCode();
        message = result.getMessage();
    }

    String code;

    String message;
}
