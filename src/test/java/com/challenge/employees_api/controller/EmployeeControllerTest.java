package com.challenge.employees_api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.challenge.employees_api.dto.response.EmployeeResponse;
import com.challenge.employees_api.entity.Employee;
import com.challenge.employees_api.service.EmployeeService;
import com.challenge.employees_api.web.ApiResponseFactory;

import jakarta.annotation.Resource;

@WebMvcTest(controllers = EmployeeController.class)
@Import(ApiResponseFactory.class)
class EmployeeControllerTest {

  @Resource
  MockMvc mvc;

  @MockitoBean
  EmployeeService service;

  @Test
  void findAll_returns200() throws Exception {
    var resp = new EmployeeResponse(
        1L, "Ana", null, "Lopez", "Perez",
        28, Employee.Gender.FEMALE, LocalDate.of(1997,5,5), "QA"
    );
    when(service.findAll()).thenReturn(List.of(resp));

    mvc.perform(get("/api/v1/employees"))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$.status").value("success"))
       .andExpect(jsonPath("$.data[0].firstName").value("Ana"));
  }
}