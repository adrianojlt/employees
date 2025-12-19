package com.virtuoso.employee.loaders;

import com.virtuoso.employee.repositories.EmployeeRepository;
import com.virtuoso.employee.repositories.entities.EmployeeEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Transactional
@SpringBootTest
public class EmployeeHierarchyIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(EmployeeHierarchyIntegrationTest.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void shouldLogManagerCyclesIfTheyExist() {

        List<EmployeeEntity> employees = employeeRepository.findAll();

        for (EmployeeEntity employee : employees) {

            EmployeeEntity manager = employee.getManager();

            if (manager == null) {
                continue;
            }

            if (EmployeeHierarchyValidator.createsCycle(employee, manager)) {
                log.warn( "Manager cycle detected: employee {} {} reports to manager {} {}",
                        employee.getFirstName(),
                        employee.getId(),
                        manager.getFirstName(),
                        manager.getId());
            }
        }
    }
}
