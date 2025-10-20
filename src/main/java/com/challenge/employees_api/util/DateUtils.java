package com.challenge.employees_api.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtils {
  private DateUtils(){}
  public static LocalDate parse(String value) {
    return LocalDate.parse(value, DateTimeFormatter.ofPattern(Constants.DATE_PATTERN));
  }
}