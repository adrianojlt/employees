package com.virtuoso.employee.repositories;

import com.virtuoso.employee.repositories.entities.DivisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DivisionRepository extends JpaRepository<DivisionEntity, Long> {
}
