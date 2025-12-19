package com.virtuoso.employee.repositories;

import com.virtuoso.employee.repositories.entities.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
}
