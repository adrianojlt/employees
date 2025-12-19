package com.virtuoso.employee.controllers;

import com.virtuoso.employee.models.EmployeeDto;
import com.virtuoso.employee.services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@Tag(name = "Employees", description = "Operations related to the employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @Operation(summary = "Retrieve employees with optional filters")
    public Page<EmployeeDto> getEmployees(
            @Parameter(description = "Filter by team ID")
            @RequestParam(required = false) Long teamId,
            @Parameter(description = "Filter by division ID")
            @RequestParam(required = false) Long divisionId,
            @Parameter(description = "Filter by manager ID")
            @RequestParam(required = false) Long managerId,
            @PageableDefault( size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return employeeService.getAllEmployees(teamId, divisionId, managerId, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve employee given there id")
    public ResponseEntity<EmployeeDto> getById(@PathVariable Long id) {
        return employeeService.getEmployee(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
