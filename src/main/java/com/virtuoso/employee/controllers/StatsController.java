package com.virtuoso.employee.controllers;

import com.virtuoso.employee.services.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/stats")
@Tag(name = "Statistics", description = "Operations related to stats")
public class StatsController {

    private final StatsService service;

    public StatsController(StatsService service) {
        this.service = service;
    }

    @GetMapping("/average-age/all")
    @Operation(summary = "Retrieve average age of all employees")
    public long companyAverageAge() {
        return service.companyAverageAge();
    }

    @GetMapping("/average-age/teams")
    @Operation(summary = "Retrieve average age of employees within a given team")
    public Map<Long, Long> byTeam() {
        return new TreeMap<>(service.averageAgeByTeam());
    }

    @GetMapping("/average-age/managers")
    @Operation(summary = "Retrieve average age of employees managed by a given manager")
    public Map<Long, Long> byManager() {
        return new TreeMap<>(service.averageAgeByManager());
    }

    @GetMapping("/average-age/divisions")
    @Operation(summary = "Retrieve average age of employees within a given division")
    public Map<Long, Long> byDivision() {
        return new TreeMap<>(service.averageAgeByDivision());
    }
}
