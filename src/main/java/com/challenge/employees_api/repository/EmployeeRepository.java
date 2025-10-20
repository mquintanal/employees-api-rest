package com.challenge.employees_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.challenge.employees_api.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {}