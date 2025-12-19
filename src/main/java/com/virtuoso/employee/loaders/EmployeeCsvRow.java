package com.virtuoso.employee.loaders;

import java.time.LocalDate;

public record EmployeeCsvRow(
        Long divisionId,
        Long teamId,
        Long managerId,
        Long employeeId,
        String firstName,
        String lastName,
        LocalDate birthdate
) {}
