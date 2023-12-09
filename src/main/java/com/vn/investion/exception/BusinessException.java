package com.vn.investion.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BusinessException extends RuntimeException{
    int code;
    String message;
    int httpStatus;
}
