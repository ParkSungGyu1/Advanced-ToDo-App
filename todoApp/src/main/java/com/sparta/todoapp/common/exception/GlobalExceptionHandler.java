package com.sparta.todoapp.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException ex) {
        return buildErrorResponse(ExceptionType.NULL_POINTER, ex);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildErrorResponse(ExceptionType.ILLEGAL_ARGUMENT, ex);
    }

    @ExceptionHandler(ResourceNotFoundException.class) // 사용자 정의 예외
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return buildErrorResponse(ExceptionType.NOT_FOUND, ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        return buildErrorResponse(ExceptionType.INTERNAL_ERROR, ex);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(ExceptionType exceptionType, Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                exceptionType.getErrorCode(),
                exceptionType.getMessage(),
                ex.getMessage(),
                exceptionType.getStatus().value()
        );
        return new ResponseEntity<>(errorResponse, exceptionType.getStatus());
    }
}
