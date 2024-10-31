package com.sparta.todoapp.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {
    NULL_POINTER("1001","Null value encountered", HttpStatus.BAD_REQUEST),
    ILLEGAL_ARGUMENT("1002","Illegal argument provided", HttpStatus.BAD_REQUEST),
    NOT_FOUND("1003","Resource not found", HttpStatus.NOT_FOUND),
    INTERNAL_ERROR("1004","Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String errorCode;
    private final String message;
    private final HttpStatus status;

    ExceptionType(String errorCode, String message, HttpStatus status) {
        this.errorCode = errorCode;
        this.message = message;
        this.status = status;
    }

}
