package com.virtuoso.employee.loaders;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class EmployeeCsvParserTest {

    private final EmployeeCsvParser parser = new EmployeeCsvParser();

    @Test
    void shouldParseCsvCorrectly() {
        List<EmployeeCsvRow> rows = parser.parse("data.csv");
        assertEquals(1370, rows.size());
    }
}
