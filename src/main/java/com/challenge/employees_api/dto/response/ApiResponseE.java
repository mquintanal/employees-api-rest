package com.challenge.employees_api.dto.response;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class ApiResponseE<T> {
  private String status;           // "success" | "error"
  private T data;                  // payload
  private String message;          // optional human message
  private OffsetDateTime timestamp;// ISO-8601
  private String path;             // request path
}