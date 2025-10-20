package com.challenge.employees_api.entity;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "employees")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Employee {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 60)
  private String firstName;

  @Column(length = 60)
  private String secondName;

  @Column(nullable = false, length = 60)
  private String lastNamePaternal;

  @Column(nullable = false, length = 60)
  private String lastNameMaternal;

  @Column(nullable = false)
  private Integer age;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private Gender gender;

  @Column(nullable = false)
  private LocalDate birthDate;

  @Column(nullable = false, length = 80)
  private String position;

  public enum Gender { MALE, FEMALE, OTHER }
}