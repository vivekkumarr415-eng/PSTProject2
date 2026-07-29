# URL Shortener Service

This repository contains an MVP URL shortener built with Java 17 and Spring Boot 3.5. It supports creating short links, resolving them, tracking click counts and last access times, listing and deleting records, and returning basic analytics.

## What is included
- REST API for URL creation and resolution
- H2-backed persistence for local development
- Validation and centralized exception handling
- Swagger/OpenAPI documentation
- Actuator health endpoint
- Unit, controller, service, and repository tests

## Project layout
- [API.md](API.md) — API reference and examples
- [SwaggerGuide.md](SwaggerGuide.md) — Swagger/OpenAPI usage guide
- [RunGuide.md](RunGuide.md) — how to build and run the service
- [ArchitectureDiagram.md](ArchitectureDiagram.md) — architecture overview
- [MermaidDiagrams.md](MermaidDiagrams.md) — sequence and component diagrams
- [DeploymentGuide.md](DeploymentGuide.md) — deployment guidance
- [Limitations.md](Limitations.md) — current constraints
- [TradeOffs.md](TradeOffs.md) — engineering trade-offs
- [docs/postman/UrlShortener.postman_collection.json](docs/postman/UrlShortener.postman_collection.json) — Postman collection

## Quick start
1. Install Java 17
2. Build the service
   ```bash
   mvn clean package
   ```
3. Run the service
   ```bash
   mvn spring-boot:run
   ```
4. Open Swagger UI at http://localhost:8080/swagger-ui.html

## Main endpoints
- POST /api/urls
- GET /api/urls
- GET /api/urls/{id}
- DELETE /api/urls/{id}
- GET /api/urls/{shortCode}
- GET /api/urls/analytics

## Notes
The current MVP uses an in-memory H2 database and returns structured JSON responses for resolution metadata rather than a full HTTP redirect flow.
