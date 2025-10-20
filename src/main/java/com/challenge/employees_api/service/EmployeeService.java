package com.challenge.employees_api.service;


import java.util.List;

import com.challenge.employees_api.dto.request.EmployeeRequest;
import com.challenge.employees_api.dto.request.EmployeeUpdateRequest;
import com.challenge.employees_api.dto.response.EmployeeResponse;

public interface EmployeeService {
  List<EmployeeResponse> findAll();
  void deleteById(Long id);
  EmployeeResponse update(Long id, EmployeeUpdateRequest patch);
  List<EmployeeResponse> createBulk(List<EmployeeRequest> requests);
}