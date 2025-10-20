package com.challenge.employees_api.dto.request;

import java.time.LocalDate;

import com.challenge.employees_api.entity.Employee.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeUpdateRequest {
  private String firstName;
  private String secondName;
  private String lastNamePaternal;
  private String lastNameMaternal;
  private Integer age;
  private Gender gender;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  private LocalDate birthDate;
  private String position;
}