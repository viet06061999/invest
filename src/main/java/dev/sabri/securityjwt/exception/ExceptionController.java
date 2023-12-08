package dev.sabri.securityjwt.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sabri.securityjwt.model.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;


@Log4j2
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {
    private final ObjectMapper objectMapper;

    @ExceptionHandler(BusinessException.class)
    protected void handleBusinessException(BusinessException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        handError(e.code,e.httpStatus, e.message, response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected void handleException(AccessDeniedException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        handError(4003,403, "No permission!", response);
    }

    @ExceptionHandler(Exception.class)
    protected void handleBusinessException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        handError(5000,500, "Some thing went wrong!", response);
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
