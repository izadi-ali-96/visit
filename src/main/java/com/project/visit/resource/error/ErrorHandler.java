package com.project.visit.resource.error;

import com.project.visit.exception.AuthException;
import com.project.visit.exception.BusinessException;
import com.project.visit.resource.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AuthException.class})
    public ResponseEntity<Void> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<ErrorResponse> handleAccessBusinessException(
            BusinessException ex, WebRequest request) {
        return ResponseEntity.unprocessableEntity().body(new ErrorResponse(ex.getResult()));
    }
}
