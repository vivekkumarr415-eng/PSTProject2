# Business Requirements Document (BRD) — URL Shortener Service

## 1. Executive Summary
This project will deliver a single-service URL shortener application that enables users to create short links from long URLs and resolve those short links back to their original destinations. The solution is intended to be a practical, production-oriented MVP that demonstrates strong engineering discipline while remaining focused on core business value.

The system will provide a streamlined API experience, preserve mappings in a local H2 database, expose documentation and health endpoints, and support testability. The implementation will remain intentionally simple and avoid unnecessary infrastructure complexity.

## 2. Business Objectives
The business objectives of this initiative are to:
- provide a reliable mechanism for shortening long URLs,
- enable seamless redirection from short links to the original destination,
- demonstrate a well-structured and maintainable software product,
- ensure documentation and observability are built into the service from the start,
- and provide a strong interview-ready engineering artifact.

## 3. Problem Statement
Long URLs are difficult to share, remember, and display in messaging and digital channels. A URL shortener provides a compact alternative that is easier to distribute and manage. The intended product must deliver this capability in a simple, reliable, and maintainable way without introducing excessive architectural overhead.

## 4. Functional Requirements
1. The system shall accept a long URL from a client.
2. The system shall generate a unique short code for the submitted URL.
3. The system shall store the relationship between the original URL and the generated short code.
4. The system shall provide an endpoint to create a short URL.
5. The system shall provide an endpoint to resolve a short code back to the original URL.
6. The system shall validate incoming URLs and reject malformed input.
7. The system shall return clear error responses for invalid requests or missing mappings.
8. The system shall expose API documentation for the available endpoints.
9. The system shall expose health and readiness endpoints for operational visibility.

## 5. Non-Functional Requirements
1. The solution shall be implemented using Java 17 and Spring Boot 3.5.x.
2. The solution shall use Maven as the build tool.
3. The solution shall use H2 as the persistence layer for the MVP.
4. The solution shall be easy to understand, test, and maintain.
5. The solution shall provide clear developer-facing documentation.
6. The solution shall expose health and readiness information.
7. The solution shall support automated unit and integration testing.

## 6. User Stories
- As an API consumer, I want to submit a long URL so that I can receive a short link for sharing.
- As an API consumer, I want to access a short link so that I can be redirected to the intended destination.
- As a developer, I want documentation so that I can understand and test the service quickly.
- As an operator, I want health endpoints so that I can verify the service is running correctly.

## 7. Acceptance Criteria
### Create Short URL
- A valid long URL can be submitted successfully.
- The service returns a generated short code and shortened URL.
- Invalid input produces a clear validation error.

### Resolve Short URL
- A valid short code redirects the client to the original URL.
- An unknown short code returns a not-found response.

### Persistence
- The mapping is stored in the H2 database.
- The mapping remains available after a restart in the local environment.

### Documentation and Monitoring
- OpenAPI/Swagger documentation is available.
- Health endpoints return usable status information.

## 8. Assumptions
- The solution will be delivered as a single Spring Boot service.
- H2 is sufficient for the MVP database needs.
- Authentication and authorization are not required for the initial release.
- The system will prioritize correctness and maintainability over advanced scale features.
- Short codes will be generated automatically by the service.

## 9. Constraints
- The solution must use the specified technology stack.
- The implementation must avoid unnecessary external infrastructure such as Redis, Kafka, Docker, Kubernetes, or cloud deployment.
- The scope must remain focused on an MVP suitable for interview review.
- The project must remain understandable and reviewable without excessive complexity.

## 10. Dependencies
- Java 17 runtime environment
- Maven build tool
- Spring Boot 3.5.x framework
- H2 database
- Spring Web, Data JPA, Validation, Lombok, OpenAPI, Actuator, JUnit5, and Mockito libraries

## 11. Risks
- Ambiguity around custom aliases and redirect behavior may affect design decisions.
- A narrow MVP scope may not satisfy all expectations if the reviewer seeks richer functionality.
- Incomplete or changing requirements could introduce rework in later phases.
- Poor validation design could lead to reliability issues around malformed or malicious URLs.

## 12. Success Metrics
Success will be measured by the following indicators:
- the service successfully creates and resolves short links,
- the core API behavior is covered by automated tests,
- documentation and health endpoints are available,
- and the solution is understandable and reviewable as a production-quality MVP.

## 13. Future Scope
Potential future enhancements include:
- support for custom short aliases,
- expiration policies for links,
- analytics and click tracking,
- improved security and abuse prevention,
- and integration with external deployment and monitoring platforms.

## 14. Engineering Notes
The implementation should follow a clean layered architecture with clear separation between API, service, persistence, and domain concerns. The MVP should prioritize correctness, validation, observability, and testability while keeping the scope manageable for a single-service interview assignment.
