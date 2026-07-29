# Deployment Guide

## Local deployment
1. Build the application with Maven.
2. Run the Spring Boot jar.
3. Access the service on port 8080.

## Container deployment option
A simple container build can be created from the Spring Boot jar:
```bash
mvn clean package
java -jar target/url-shortener-service-0.0.1-SNAPSHOT.jar
```

## Production considerations
- Replace H2 with PostgreSQL or MySQL.
- Configure environment variables for database credentials.
- Enable TLS and proper secret management.
- Add reverse proxy and monitoring.
