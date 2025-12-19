package com.virtuoso.employee.models;

import java.time.LocalDate;

public record EmployeeDto(

        Long id,

        String firstName,
        String lastName,

        LocalDate birthdate,

        Long teamId,
        Long managerId,
        Long divisionId
) {
    public String fullName() {
        return firstName + " " + lastName;
    }
}
