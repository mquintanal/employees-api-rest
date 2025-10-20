package com.challenge.employees_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.employees_api.dto.request.EmployeeRequest;
import com.challenge.employees_api.dto.request.EmployeeUpdateRequest;
import com.challenge.employees_api.dto.response.ApiResponseE;
import com.challenge.employees_api.dto.response.EmployeeResponse;
import com.challenge.employees_api.exception.ApiError;
import com.challenge.employees_api.service.EmployeeService;
import com.challenge.employees_api.web.ApiResponseFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
@Tag(name = "Employees", description = "Operations for Employee management")
public class EmployeeController {

  private final EmployeeService service;
  private final ApiResponseFactory responseFactory;

  @Operation(
      summary = "Get all employees",
      description = "Retrieves the complete list of registered employees.",
      responses = {
          @ApiResponse(responseCode = "200", description = "Employees retrieved",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ApiResponseEmployees.class)))
      }
  )
  @GetMapping
  public ResponseEntity<ApiResponseE<List<EmployeeResponse>>> getAll(HttpServletRequest request) {
    log.debug("GET /employees - retrieving all employees");
    var data = service.findAll();
    return ResponseEntity.ok(responseFactory.success(data, "Employees retrieved", request));
  }

  @Operation(
      summary = "Create one or many employees",
      description = "Inserts one or multiple employees in a single request.",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          required = true,
          content = @Content(mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = EmployeeRequest.class)),
              examples = @ExampleObject(value = """
              [
                {
                  "firstName":"Juan","secondName":"Carlos","lastNamePaternal":"Hernandez",
                  "lastNameMaternal":"Lopez","age":29,"gender":"MALE",
                  "birthDate":"15-08-1996","position":"Backend Engineer"
                }
              ]
              """)
          )
      ),
      responses = {
          @ApiResponse(responseCode = "200", description = "Employees created",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ApiResponseEmployees.class))),
          @ApiResponse(responseCode = "400", description = "Validation error",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ApiError.class)))
      }
  )
  @PostMapping
  public ResponseEntity<ApiResponseE<List<EmployeeResponse>>> create(
      @Valid @RequestBody List<@Valid EmployeeRequest> requests,
      HttpServletRequest request) {
    log.debug("POST /employees - creating {} employees", requests.size());
    var data = service.createBulk(requests);
    return ResponseEntity.ok(responseFactory.success(data, "Employees created", request));
  }

  @Operation(
      summary = "Partially update an employee",
      description = "Updates any provided field of the employee (PATCH semantics).",
      parameters = {
          @Parameter(name = "id", description = "Employee id", required = true, example = "1")
      },
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          required = true,
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = EmployeeUpdateRequest.class),
              examples = @ExampleObject(value = """
              { "firstName":"Ana", "position":"QA Lead" }
              """)
          )
      ),
      responses = {
          @ApiResponse(responseCode = "200", description = "Employee updated",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ApiResponseEmployee.class))),
          @ApiResponse(responseCode = "404", description = "Employee not found",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ApiError.class)))
      }
  )
  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponseE<EmployeeResponse>> update(
      @PathVariable @Min(1) Long id,
      @Valid @RequestBody EmployeeUpdateRequest patch,
      HttpServletRequest request) {
    log.debug("PATCH /employees/{} - updating fields: {}", id, patch);
    var data = service.update(id, patch);
    return ResponseEntity.ok(responseFactory.success(data, "Employee updated", request));
  }

  @Operation(
      summary = "Delete an employee by id",
      description = "Deletes the employee identified by id.",
      parameters = {
          @Parameter(name = "id", description = "Employee id", required = true, example = "1")
      },
      responses = {
          @ApiResponse(responseCode = "200", description = "Employee deleted",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ApiResponseVoid.class))),
          @ApiResponse(responseCode = "404", description = "Employee not found",
              content = @Content(mediaType = "application/json",
                  schema = @Schema(implementation = ApiError.class)))
      }
  )
  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponseE<Void>> delete(@PathVariable @Min(1) Long id, HttpServletRequest request) {
    log.debug("DELETE /employees/{} - deleting employee", id);
    service.deleteById(id);
    return ResponseEntity.ok(responseFactory.success(null, "Employee deleted", request));
  }

  /** Clases fantasma para documentar el wrapper tipado en Swagger */
  @Schema(name = "ApiResponseEmployees", description = "Standard response with employees list")
  private static class ApiResponseEmployees extends ApiResponseWrapper<List<EmployeeResponse>> {}
  @Schema(name = "ApiResponseEmployee", description = "Standard response with a single employee")
  private static class ApiResponseEmployee extends ApiResponseWrapper<EmployeeResponse> {}
  @Schema(name = "ApiResponseVoid", description = "Standard response without data")
  private static class ApiResponseVoid extends ApiResponseWrapper<Void> {}

  /** Wrapper genérico usado sólo para la documentación de Swagger */
  @Schema(name = "ApiResponseE")
  private static class ApiResponseWrapper<T> {
    public String status;
    public T data;
    public String message;
    public String timestamp;
    public String path;
  }
}