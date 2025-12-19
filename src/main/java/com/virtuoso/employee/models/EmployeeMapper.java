package com.virtuoso.employee.models;

import com.virtuoso.employee.repositories.entities.EmployeeEntity;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public EmployeeDto toDto(EmployeeEntity entity) {
        return new EmployeeDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthdate(),
                entity.getTeam().getId(),
                entity.getManager() != null ? entity.getManager().getId() : null,
                entity.getDivision().getId()
        );
    }
}
