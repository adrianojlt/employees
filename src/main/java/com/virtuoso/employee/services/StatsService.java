package com.virtuoso.employee.services;

import com.virtuoso.employee.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatsService {

    private final AgeCalculator ageCalculator;
    private final EmployeeRepository employeeRepository;

    public StatsService(
            AgeCalculator ageCalculator,
            EmployeeRepository employeeRepository) {
        this.ageCalculator = ageCalculator;
        this.employeeRepository = employeeRepository;
    }

    public long companyAverageAge() {
        return ageCalculator.averageAge(employeeRepository.findAll());
    }

    public Map<Long, Long> averageAgeByDivision() {
        return employeeRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        e -> e.getDivision().getId(),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                ageCalculator::averageAge
                        )
                ));
    }

    public Map<Long, Long> averageAgeByTeam() {
        return employeeRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        e -> e.getTeam().getId(),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                ageCalculator::averageAge
                        )
                ));
    }

    public Map<Long, Long> averageAgeByManager() {
        return employeeRepository.findAll().stream()
                .filter(e -> e.getManager() != null)
                .collect(Collectors.groupingBy(
                        e -> e.getManager().getId(),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                ageCalculator::averageAge
                        )
                ));
    }
}
