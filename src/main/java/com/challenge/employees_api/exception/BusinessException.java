package com.challenge.employees_api.exception;

public class BusinessException extends RuntimeException {
  public BusinessException(String message) { super(message); }
}