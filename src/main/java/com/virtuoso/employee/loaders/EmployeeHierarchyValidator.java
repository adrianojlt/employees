package com.virtuoso.employee.loaders;

import com.virtuoso.employee.repositories.entities.EmployeeEntity;

import java.util.HashSet;
import java.util.Set;

public class EmployeeHierarchyValidator {

    public static boolean createsCycle(EmployeeEntity employee, EmployeeEntity manager) {

        Set<Long> visited = new HashSet<>();

        EmployeeEntity current = manager;

        while (current != null) {

            if (current.getId().equals(employee.getId())) {
                return true;
            }

            if (!visited.add(current.getId())) {
                return false;
            }

            current = current.getManager();
        }

        return false;
    }
}
