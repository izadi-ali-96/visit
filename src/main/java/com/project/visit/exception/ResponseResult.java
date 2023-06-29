package com.project.visit.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseResult {

    DOCTOR_NOT_FOUND("100", "doctor.not.found"),
    ADDRESS_NOT_FOUND("101", "address.not.found"),
    CITY_NOT_FOUND("102", "city.not.found"),

    INVALID_ACCESS("1000", "invalid.access"),
    VISIT_NOT_FOUND("2000", "visit.not.found"),
    VISIT_ASSIGNED("2001", "visit.assigned"),

    VISIT_EXIST_IN_THIS_TIME("2001", "visit.exist.in.this.time"),

    VISIT_NOT_BELONG_TO_USER("2002", "visit.not.belong.to.user"),

    VISIT_INVALID_TIME("2003", "visit.invalid.time"),
    USER_NOT_FOUND("3000", "user.not.found"),

    NOT_PERMIT_EXCEPTION("4009", "not.permit.exception"),

    TOKEN_EXPIRE("5005", "token.expire"),

    INVALID_COMMENT_OWNER("6000", "invalid.comment.owner");
    private final String code;

    private final String message;
}
