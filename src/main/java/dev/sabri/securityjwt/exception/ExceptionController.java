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
        response.setStatus(e.httpStatus);
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        var errorRes = Response.ofFailed(e.code, e.message);
        byte[] body = objectMapper.writeValueAsBytes(errorRes);
        log.error(e);
        response.setContentLength(body.length);
        response.getOutputStream().write(body);
    }
}
