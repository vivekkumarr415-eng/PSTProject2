# Trade-offs

- H2 was chosen for rapid local development and testability over production database durability.
- The architecture favors clarity and maintainability over distributed scalability.
- The service uses a simple short-code generator and repository model rather than a more complex ID strategy.
- The analytics layer is intentionally lightweight to keep the MVP easy to understand and extend.
