package com.virtuoso.employee.repositories.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "team")
public class TeamEntity {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "division_id", nullable = false)
    private DivisionEntity division;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<EmployeeEntity> employees;
}
