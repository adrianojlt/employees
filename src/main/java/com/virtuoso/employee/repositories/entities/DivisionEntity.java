package com.virtuoso.employee.repositories.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "division")
public class DivisionEntity {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "division", fetch = FetchType.LAZY)
    private List<TeamEntity> teams;

    @OneToMany(mappedBy = "division", fetch = FetchType.LAZY)
    private List<EmployeeEntity> employees;
}
