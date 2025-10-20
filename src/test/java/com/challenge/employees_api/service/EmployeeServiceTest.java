package com.challenge.employees_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.challenge.employees_api.dto.request.EmployeeRequest;
import com.challenge.employees_api.dto.response.EmployeeResponse;
import com.challenge.employees_api.entity.Employee;
import com.challenge.employees_api.mapper.EmployeeMapper;
import com.challenge.employees_api.repository.EmployeeRepository;
import com.challenge.employees_api.service.impl.EmployeeServiceImpl;

class EmployeeServiceTest {

  @Test
  void createBulk_shouldPersistAndReturnResponses() {
    var repo = mock(EmployeeRepository.class);
    var mapper = mock(EmployeeMapper.class);
    var svc = new EmployeeServiceImpl(repo, mapper);

    var req = EmployeeRequest.builder()
        .firstName("John").lastNamePaternal("Doe").lastNameMaternal("Smith")
        .age(30).gender(Employee.Gender.MALE).birthDate(LocalDate.of(1995,1,1))
        .position("Engineer").build();

    var entity = Employee.builder().id(1L).firstName("John").lastNamePaternal("Doe")
        .lastNameMaternal("Smith").age(30).gender(Employee.Gender.MALE)
        .birthDate(LocalDate.of(1995,1,1)).position("Engineer").build();

    when(mapper.toEntity(req)).thenReturn(entity);
    when(repo.saveAll(anyList())).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenAnswer(i -> {
      var e = (Employee)i.getArgument(0);
      return new EmployeeResponse(
          e.getId(), e.getFirstName(), e.getSecondName(), e.getLastNamePaternal(),
          e.getLastNameMaternal(), e.getAge(), e.getGender(), e.getBirthDate(), e.getPosition());
    });

    var out = svc.createBulk(List.of(req));
    assertThat(out).hasSize(1);
    assertThat(out.get(0).getId()).isEqualTo(1L);
    verify(repo, times(1)).saveAll(anyList());
  }
}