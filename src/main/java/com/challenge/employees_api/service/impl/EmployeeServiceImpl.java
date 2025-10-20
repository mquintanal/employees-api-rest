package com.challenge.employees_api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.employees_api.dto.request.EmployeeRequest;
import com.challenge.employees_api.dto.request.EmployeeUpdateRequest;
import com.challenge.employees_api.dto.response.EmployeeResponse;
import com.challenge.employees_api.entity.Employee;
import com.challenge.employees_api.exception.NotFoundException;
import com.challenge.employees_api.mapper.EmployeeMapper;
import com.challenge.employees_api.repository.EmployeeRepository;
import com.challenge.employees_api.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository repository;
  private final EmployeeMapper mapper;

  @Override @Transactional(readOnly = true)
  public List<EmployeeResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  @Override @Transactional
  public void deleteById(Long id) {
    if (!repository.existsById(id)) throw new NotFoundException("Employee not found: " + id);
    repository.deleteById(id);
  }

  @Override @Transactional
  public EmployeeResponse update(Long id, EmployeeUpdateRequest patch) {
    Employee e = repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Employee not found: " + id));
    mapper.updateEntityFromPatch(patch, e);
    return mapper.toResponse(repository.save(e));
  }

  @Override @Transactional
  public List<EmployeeResponse> createBulk(List<EmployeeRequest> requests) {
    List<Employee> toSave = requests.stream().map(mapper::toEntity).toList();
    return repository.saveAll(toSave).stream().map(mapper::toResponse).toList();
  }
}