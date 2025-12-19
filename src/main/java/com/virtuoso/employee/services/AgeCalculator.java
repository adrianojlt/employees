package com.virtuoso.employee.services;

import com.virtuoso.employee.repositories.entities.EmployeeEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;

@Component
public class AgeCalculator {

    public long averageAge(Collection<EmployeeEntity> employees) {

        LocalDate now = LocalDate.now();

        double average = employees.stream()
                .mapToInt(e -> Period.between(e.getBirthdate(), now).getYears())
                .average()
                .orElse(0.0);

        return Math.round(average);
    }
}
