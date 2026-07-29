# Limitations

- The current implementation is an MVP and does not include full redirect semantics.
- Persistence uses H2, which is suitable for development but not production-grade durability.
- Analytics are limited to simple counts and top-clicked entries.
- No authentication, rate limiting, or abuse protection is included.
- The service does not yet support custom aliases or expiration policy configuration beyond stored timestamps.
