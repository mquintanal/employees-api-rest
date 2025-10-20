package com.challenge.employees_api.exception;

import java.time.OffsetDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiError> handleNotFound(NotFoundException ex, HttpServletRequest req) {
    ApiError err = ApiError.builder()
        .code("NOT_FOUND")
        .message(ex.getMessage())
        .path(req.getRequestURI())
        .status(HttpStatus.NOT_FOUND.value())
        .timestamp(OffsetDateTime.now())
        .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
    var fieldErrors = ex.getBindingResult().getFieldErrors().stream()
        .map(fe -> Map.of("field", fe.getField(), "error", fe.getDefaultMessage()))
        .toList();
    ApiError err = ApiError.builder()
        .code("VALIDATION_ERROR")
        .message("Invalid request")
        .path(req.getRequestURI())
        .status(HttpStatus.BAD_REQUEST.value())
        .timestamp(OffsetDateTime.now())
        .details(fieldErrors)
        .build();
    return ResponseEntity.badRequest().body(err);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req) {
    ApiError err = ApiError.builder()
        .code("INTERNAL_ERROR")
        .message("Unexpected error")
        .path(req.getRequestURI())
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .timestamp(OffsetDateTime.now())
        .build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
  }
}