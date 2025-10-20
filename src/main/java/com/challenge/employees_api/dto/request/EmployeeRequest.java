package com.challenge.employees_api.dto.request;

import java.time.LocalDate;

import com.challenge.employees_api.entity.Employee.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class EmployeeRequest {
  @NotBlank @Size(max = 60) private String firstName;
  @Size(max = 60) private String secondName;
  @NotBlank @Size(max = 60) private String lastNamePaternal;
  @NotBlank @Size(max = 60) private String lastNameMaternal;

  @NotNull @Min(18) @Max(100) private Integer age;
  @NotNull private Gender gender;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
  private LocalDate birthDate;

  @NotBlank @Size(max = 80) private String position;
}