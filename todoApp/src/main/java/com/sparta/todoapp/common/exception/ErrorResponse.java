package com.sparta.todoapp.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String errorCode;
    private String message;
    private String detailMessage;
    private int status;
    private long timestamp;

    public ErrorResponse(String errorCode, String message,String detailMessage, int status) {
        this.errorCode = errorCode;
        this.message = message;
        this.detailMessage =detailMessage;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
    }

}
