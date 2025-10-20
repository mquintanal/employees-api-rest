package com.challenge.employees_api.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.challenge.employees_api.dto.request.EmployeeRequest;
import com.challenge.employees_api.dto.request.EmployeeUpdateRequest;
import com.challenge.employees_api.dto.response.EmployeeResponse;
import com.challenge.employees_api.entity.Employee;

@Mapper(
    componentModel = "spring",                      
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface EmployeeMapper {
  Employee toEntity(EmployeeRequest request);
  EmployeeResponse toResponse(Employee entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntityFromPatch(EmployeeUpdateRequest patch, @MappingTarget Employee entity);
}