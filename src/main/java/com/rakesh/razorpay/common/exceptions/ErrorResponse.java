package com.rakesh.razorpay.common.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.boot.web.error.Error;

import java.time.LocalDateTime;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL) //This does not include null values key in the response
public record ErrorResponse (
    String errorCode,
    String errorDescription,
    LocalDateTime timeStamp,
    List<FieldError> fieldErrors
){
    public record FieldError(
        String field,
        String message
    ){}
    public static ErrorResponse of(String errorCode, String errorDescription, List<FieldError> fieldErrors) {
        return new ErrorResponse(errorCode, errorDescription, LocalDateTime.now(), fieldErrors);
    }
    public static ErrorResponse of(String errorCode, String errorDescription) {
        return new ErrorResponse(errorCode, errorDescription, LocalDateTime.now(), List.of());
    }
}
