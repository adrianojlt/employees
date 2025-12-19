package com.virtuoso.employee.services;

import com.virtuoso.employee.repositories.entities.EmployeeEntity;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecifications {

    public static Specification<EmployeeEntity> hasTeam(Long teamId) {
        return (root, query, cb) ->
                cb.equal(root.get("team").get("id"), teamId);
    }

    public static Specification<EmployeeEntity> hasDivision(Long divisionId) {
        return (root, query, cb) ->
                cb.equal(root.get("division").get("id"), divisionId);
    }

    public static Specification<EmployeeEntity> hasManager(Long managerId) {
        return (root, query, cb) ->
                cb.equal(root.get("manager").get("id"), managerId);
    }
}
