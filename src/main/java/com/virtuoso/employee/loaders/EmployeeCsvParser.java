package com.virtuoso.employee.loaders;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Component
public class EmployeeCsvParser {

    private static final DateTimeFormatter CSV_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-M-d");

    public List<EmployeeCsvRow> parse(String resourcePath) {
        try (
                InputStream is = getClass()
                        .getClassLoader()
                        .getResourceAsStream(resourcePath);
                InputStreamReader reader = new InputStreamReader(
                        Objects.requireNonNull(is, "CSV file not found"),
                        StandardCharsets.UTF_8
                );
                CSVReader csvReader = new CSVReaderBuilder(reader)
                        .withSkipLines(1)
                        .build();
        ) {
            return csvReader.readAll().stream()
                    .map(this::mapRow)
                    .toList();

        } catch (Exception e) {
            throw new IllegalStateException("Failed to parse CSV file", e);
        }
    }

    private EmployeeCsvRow mapRow(String[] row) {
        return new EmployeeCsvRow(
                Long.parseLong(row[0]),
                Long.parseLong(row[1]),
                parseNullableLong(row[2]),
                Long.parseLong(row[3]),
                row[4],
                row[5],
                LocalDate.parse(row[6], CSV_DATE_FORMAT)
        );
    }

    private Long parseNullableLong(String value) {
        return value == null || value.isBlank() ? null : Long.parseLong(value);
    }
}
