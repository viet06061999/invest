package com.vn.investion.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vn.investion.dto.Response;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(BusinessException.class)
    protected void handleBusinessException(BusinessException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error("BusinessException:", e);
        handError(e.code,e.httpStatus, e.message, response);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    protected void handleExpiredJwtException(ExpiredJwtException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error("ExpiredJwtException:", e);
        handError(5000,500, e.getMessage(), response);
    }


    @ExceptionHandler(AccessDeniedException.class)
    protected void handleException(AccessDeniedException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error("AccessDeniedException:", e);
        handError(4003,403, "No permission!", response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected void handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error("MethodArgumentNotValidException:", e);
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        response.setStatus(400);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        var errorRes = Response.ofFailed(4000, "Invalid input", fieldErrors);
        byte[] body = objectMapper.writeValueAsBytes(errorRes);
        objectMapper.writeValueAsString(errorRes);
        response.setContentLength(body.length);
        response.getOutputStream().write(body);
    }
    @ExceptionHandler(Exception.class)
    protected void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error("Exception:", e);
        handError(5000,500, e.getMessage(), response);
    }
    private void handError(int code, int status, String message, HttpServletResponse response) throws IOException {
        response.setStatus(status);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        var errorRes = Response.ofFailed(code, message);
        byte[] body = objectMapper.writeValueAsBytes(errorRes);
        response.setContentLength(body.length);
        response.getOutputStream().write(body);
    }
}
