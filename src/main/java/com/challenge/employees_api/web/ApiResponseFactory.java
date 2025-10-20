package com.challenge.employees_api.web;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Component;

import com.challenge.employees_api.dto.response.ApiResponseE;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class ApiResponseFactory {

  public <T> ApiResponseE<T> success(T data, String message, HttpServletRequest req) {
    return ApiResponseE.<T>builder()
        .status("success")
        .data(data)
        .message(message)
        .timestamp(OffsetDateTime.now())
        .path(req != null ? req.getRequestURI() : null)
        .build();
  }
}