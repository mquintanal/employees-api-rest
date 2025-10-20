package com.challenge.employees_api.dto.response;

import java.time.LocalDate;

import com.challenge.employees_api.entity.Employee.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class EmployeeResponse {
  private Long id;
  private String firstName;
  private String secondName;
  private String lastNamePaternal;
  private String lastNameMaternal;
  private Integer age;
  private Gender gender;
  private LocalDate birthDate;
  private String position;
}