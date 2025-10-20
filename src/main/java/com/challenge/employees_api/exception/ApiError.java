package com.challenge.employees_api.exception;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class ApiError {
  private String code;        // e.g., "EMPLOYEE_NOT_FOUND"
  private String message;
  private String path;
  private int status;         // HTTP status numeric
  private OffsetDateTime timestamp;
  private Object details;     // optional
}