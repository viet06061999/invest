package com.vn.investion.exception;

public class BusinessException extends RuntimeException {
    int code;
    String message;
    int httpStatus;

    public BusinessException(int code,
                             String message,
                             int httpStatus) {
        super(message);
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
