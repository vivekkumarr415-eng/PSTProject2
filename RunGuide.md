# Run Guide

## Prerequisites
- Java 17+
- Maven 3.8+

## Build
```bash
mvn clean package
```

## Run locally
```bash
mvn spring-boot:run
```

## Run with a different port
```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=9090
```

## Health check
```bash
curl http://localhost:8080/actuator/health
```

## Swagger UI
Open: http://localhost:8080/swagger-ui.html
