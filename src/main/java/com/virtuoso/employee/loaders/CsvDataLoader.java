package com.virtuoso.employee.loaders;

import com.virtuoso.employee.repositories.DivisionRepository;
import com.virtuoso.employee.repositories.EmployeeRepository;
import com.virtuoso.employee.repositories.TeamRepository;
import com.virtuoso.employee.repositories.entities.DivisionEntity;
import com.virtuoso.employee.repositories.entities.EmployeeEntity;
import com.virtuoso.employee.repositories.entities.TeamEntity;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class CsvDataLoader implements ApplicationRunner {

    private final EmployeeCsvParser csvParser;
    private final TeamRepository teamRepository;
    private final EmployeeRepository employeeRepository;
    private final DivisionRepository divisionRepository;

    public CsvDataLoader(
            EmployeeCsvParser csvParser,
            TeamRepository teamRepository,
            EmployeeRepository employeeRepository,
            DivisionRepository divisionRepository) {
        this.csvParser = csvParser;
        this.teamRepository = teamRepository;
        this.employeeRepository = employeeRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {

        if (employeeRepository.count() > 0) {
            log.info("Database already contains data. Skipping CSV import.");
            return;
        }

        log.info("Starting CSV data import...");

        List<EmployeeCsvRow> rows = csvParser.parse("data.csv");

        Map<Long, DivisionEntity> divisions = new HashMap<>();
        Map<Long, TeamEntity> teams = new HashMap<>();
        Map<Long, EmployeeEntity> employees = new HashMap<>();

        // Divisions
        for (EmployeeCsvRow row : rows) {
            divisions.computeIfAbsent(row.divisionId(), id -> {
                DivisionEntity division = new DivisionEntity();
                division.setId(id);
                return divisionRepository.save(division);
            });
        }

        // Teams
        for (EmployeeCsvRow row : rows) {
            teams.computeIfAbsent(row.teamId(), id -> {
                TeamEntity team = new TeamEntity();
                team.setId(id);
                team.setDivision(divisions.get(row.divisionId()));
                return teamRepository.save(team);
            });
        }

        // Employees
        for (EmployeeCsvRow row : rows) {
            EmployeeEntity employee = new EmployeeEntity();
            employee.setId(row.employeeId());
            employee.setFirstName(row.firstName());
            employee.setLastName(row.lastName());
            employee.setBirthdate(row.birthdate());
            employee.setDivision(divisions.get(row.divisionId()));
            employee.setTeam(teams.get(row.teamId()));

            EmployeeEntity managedEmployee = employeeRepository.save(employee);
            employees.put(managedEmployee.getId(), managedEmployee);
        }

        // Resolve managers
        for (EmployeeCsvRow row : rows) {

            if (row.managerId() == null) {
                continue;
            }

            EmployeeEntity employee = employees.get(row.employeeId());
            EmployeeEntity manager = employees.get(row.managerId());

            if (manager == null) {
                log.warn("Manager {} not found for employee {}", row.managerId(), row.employeeId());
                continue;
            }

            if (employee.getId().equals(manager.getId())) {
                log.warn("Employee {} cannot manage themselves", employee.getId());
                continue;
            }

            if (EmployeeHierarchyValidator.createsCycle(employee, manager)) {
                log.warn("Cycle management detected between: {} with id {} and {} with id {}",
                        employee.getFirstName(),
                        employee.getId(),
                        manager.getFirstName(),
                        manager.getId());
            }

            employee.setManager(manager);
        }

        log.info("CSV data import completed successfully.");
    }
}
