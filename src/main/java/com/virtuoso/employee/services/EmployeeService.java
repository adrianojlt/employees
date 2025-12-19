package com.virtuoso.employee.services;

import com.virtuoso.employee.models.EmployeeDto;
import com.virtuoso.employee.models.EmployeeMapper;
import com.virtuoso.employee.repositories.EmployeeRepository;
import com.virtuoso.employee.repositories.entities.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    public EmployeeService(
            EmployeeMapper employeeMapper,
            EmployeeRepository employeeRepository) {
        this.employeeMapper = employeeMapper;
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    public Page<EmployeeDto> getAllEmployees(
            Long teamId,
            Long divisionId,
            Long managerId,
            Pageable pageable
    ) {

        Specification<EmployeeEntity> spec = (root, query, cb) ->
                cb.conjunction();

        if (teamId != null) {
            spec = spec.and(EmployeeSpecifications.hasTeam(teamId));
        }
        if (divisionId != null) {
            spec = spec.and(EmployeeSpecifications.hasDivision(divisionId));
        }
        if (managerId != null) {
            spec = spec.and(EmployeeSpecifications.hasManager(managerId));
        }

        return employeeRepository
                .findAll(spec, pageable)
                .map(employeeMapper::toDto);
    }

    public Optional<EmployeeDto> getEmployee(Long id) {
        return employeeRepository
                .findById(id)
                .map(employeeMapper::toDto);
    }
}
