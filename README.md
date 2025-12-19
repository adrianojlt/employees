# Employee Survey API

A REST API built with Java and Spring Boot to process and serve employee survey data. This project was done as a learning exercise to explore **Spring Data JPA**, **MariaDB**, and **API design** best practices.

## Overview

The application loads employee data from a CSV file into a relational database and exposes it through a RESTful API with filtering, sorting, pagination, and statistics endpoints.

### Features

- **CSV data ingestion** into a MariaDB database at startup
- **Flexible filtering** by division, team, and manager
- **Sorting** by any field (ascending/descending), including multi-field sorting (e.g., sort by full name)
- **Pagination** support via Spring Data JPA
- **Average age statistics** across the company, by division, by team, and by manager
- **Bearer token authentication**
- **Swagger UI** for API documentation and testing
- **Cycle detection** in management hierarchies
- **Docker** deployment with Nginx reverse proxy

## Tech Stack

- **Java 17** + **Spring Boot**
- **Spring Data JPA** + **Hibernate**
- **MariaDB**
- **Gradle** (build system)
- **Lombok**
- **OpenCSV** (CSV parsing)
- **SpringDoc OpenAPI** (Swagger UI)
- **Docker**

## Data Model

The CSV dataset contains employee records with the following structure:

```csv
divisionId, teamId, managerId, employeeId, firstName, lastName, birthdate
```

A relational database was chosen due to the referential nature of the data (divisions, teams, managers, employees). The dataset includes circular management references (e.g., employee A manages B, and B manages A), which are handled by using flat DTOs and explicit navigation endpoints to avoid recursive serialization.

## API Design

Rather than nested REST resources (`/teams/{id}/employees`), the API uses a flat resource with query parameter filters. This was chosen because the data shows that the same manager can appear across teams, and the same division across teams, so nesting wouldn't accurately represent the relationships.

### Endpoints

**Employees**
```
GET /employees                          # All employees (default sorted by ID)
GET /employees?divisionId=3&teamId=16   # Filtered by division and team
GET /employees?sort=firstName,asc&sort=lastName,asc  # Sorted by full name
GET /employees?page=0&size=100          # Paginated
```

**Average Age Statistics**
```
GET /stats/average-age/all              # Company-wide average age
GET /stats/average-age/divisions        # Average age per division
GET /stats/average-age/teams            # Average age per team
GET /stats/average-age/managers         # Average age per manager
```

All requests require a Bearer token in the `Authorization` header.

## Running Locally

### Prerequisites

- Java 17+
- MariaDB instance
- Gradle

### Build

```bash
./gradlew clean build
```

### Run

Configure your database connection in `application-dev.yml`, then:

```bash
./gradlew bootRun
```

## Docker Deployment

```bash
# Build the project
./gradlew clean build

# Build the Docker image
docker buildx build --platform linux/amd64 -t employees .

# Run the container
docker run --restart unless-stopped -d -p 8282:8282 --name employees employees:latest
```



