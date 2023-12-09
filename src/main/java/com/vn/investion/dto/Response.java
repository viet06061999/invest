package com.vn.investion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.validation.FieldError;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private T data;
    private Metadata meta = new Metadata();

    Response(T data, Metadata meta) {
        this.data = data;
        this.meta = meta;
    }

    public Response() {
    }

    public static <T> Response<T> ofSucceeded() {
        return ofSucceeded((T) null);
    }

    @SuppressWarnings("unchecked")
    public static <T> Response<T> ofSucceeded(T data) {
        Response<T> response = new Response<>();
        response.data = data;
        response.meta.code = Metadata.OK_CODE;
        return response;
    }

    public static <T> Response<List<T>> ofSucceeded(Page<T> data) {
        Response<List<T>> response = new Response<>();
        response.data = data.getContent();
        response.meta.code = Metadata.OK_CODE;
        response.meta.page = data.getNumber();
        response.meta.size = data.getSize();
        response.meta.total = data.getTotalElements();
        return response;
    }


    public static Response<Void> ofFailed(int errorCode, String message, List<FieldError> fieldErrors) {
        Response response = new Response<>();
        response.meta.code = errorCode;
        response.meta.message = message;
        response.meta.fieldErrors = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        return response;
    }

    public static Response<Void> ofFailed(int errorCode, String message) {
        Response response = new Response<>();
        response.meta.code = errorCode;
        response.meta.message = message;
        return response;
    }

    public T getData() {
        return data;
    }

    public Metadata getMeta() {
        return meta;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class Metadata {
        public static final int OK_CODE = 200;
        int code;
        Integer page;
        Integer size;
        Long total;
        String message;

        List<String> fieldErrors;

        public Metadata() {
        }

        public Metadata(int code, Integer page, Integer size, Long total, String message, List<FieldError> fieldErrors) {
            this.code = code;
            this.page = page;
            this.size = size;
            this.total = total;
            this.message = message;
            this.fieldErrors = fieldErrors.stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
        }
    }
}