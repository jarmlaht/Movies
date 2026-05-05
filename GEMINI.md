# Movies Project Instructions

This file provides architectural guidance and development workflows for the Movies application.

## Tech Stack
- **Language:** Java 17+
- **Framework:** Spring Boot
- **Database:** MongoDB
- **Build Tool:** Maven

## Architecture & Conventions
- **Layered Architecture:** Follow the established pattern:
    - `controller`: REST API endpoints
    - `service`: Business logic (Interfaces in `service`, implementations in `service.impl` or similar)
    - `repository`: Data access (Spring Data MongoDB)
    - `model`: Domain entities
- **Naming:** Use PascalCase for classes and camelCase for methods/variables.
- **Testing:** 
    - Use JUnit 5 and Mockito for unit and integration tests.
    - Ensure new features have corresponding test coverage in `src/test/java`.

## Common Workflows
- **Build:** `./mvnw clean install`
- **Run Locally:** `./mvnw spring-boot:run`
- **Run with Docker:** `docker-compose up --build`
- **Tests:** `./mvnw test`
