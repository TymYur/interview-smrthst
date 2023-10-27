package com.ytym.interview.shost.configuration;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    public ResponseEntity<Object> handleViolationExceptions(
            ConstraintViolationException ex, WebRequest request) {

        ProblemDetail detail = buildProblemDetail(request, ex.getMessage());
        return ResponseEntity.of(detail).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public ResponseEntity<Object> handleMissedParamsExceptions(
            MissingServletRequestParameterException ex, WebRequest request) {

        ProblemDetail detail = buildProblemDetail(request, ex.getMessage());
        return ResponseEntity.of(detail).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseBody
    public ResponseEntity<Object> handleWrongTypeForParamsExceptions(
            MethodArgumentTypeMismatchException ex, WebRequest request) {

        ProblemDetail detail = buildProblemDetail(request, ex.getMessage());
        return ResponseEntity.of(detail).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public ResponseEntity<Object> handleWrongFormatOfDataInputExceptions(
            HttpMessageNotReadableException ex, WebRequest request) {

        ProblemDetail detail = buildProblemDetail(request, ex.getMessage());
        return ResponseEntity.of(detail).build();
    }

    private ProblemDetail buildProblemDetail(
            WebRequest request, String errorMessage) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorMessage);
        detail.setInstance(URI.create(((ServletWebRequest) request).getRequest().getRequestURI()));
        return detail;
    }
}
