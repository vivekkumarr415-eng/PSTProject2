# API Reference

## Base URL
- http://localhost:8080

## Endpoints

### Create a short URL
- Method: POST
- Path: /api/urls
- Headers: Optional X-Base-Url
- Request body:
```json
{
  "originalUrl": "https://example.com"
}
```
- Response:
```json
{
  "success": true,
  "message": "URL created successfully",
  "data": {
    "shortCode": "abc123",
    "shortUrl": "http://localhost:8080/abc123"
  }
}
```

### List URLs
- Method: GET
- Path: /api/urls
- Response:
```json
{
  "success": true,
  "message": "URLs retrieved successfully",
  "data": [
    {
      "id": 1,
      "shortCode": "abc123",
      "originalUrl": "https://example.com",
      "shortUrl": "http://localhost:8080/abc123",
      "clickCount": 2,
      "lastAccessedAt": "2026-07-30T10:00:00",
      "expiresAt": null
    }
  ]
}
```

### Get URL by ID
- Method: GET
- Path: /api/urls/{id}
- Response:
```json
{
  "success": true,
  "message": "URL retrieved successfully",
  "data": {
    "id": 1,
    "shortCode": "abc123",
    "originalUrl": "https://example.com",
    "shortUrl": "http://localhost:8080/abc123",
    "clickCount": 2,
    "lastAccessedAt": "2026-07-30T10:00:00",
    "expiresAt": null
  }
}
```

### Delete URL by ID
- Method: DELETE
- Path: /api/urls/{id}
- Response:
```json
{
  "success": true,
  "message": "URL deleted successfully",
  "data": null
}
```

### Resolve a short URL
- Method: GET
- Path: /api/urls/{shortCode}
- Response:
```json
{
  "success": true,
  "message": "URL resolved successfully",
  "data": {
    "originalUrl": "https://example.com",
    "clickCount": 3,
    "lastAccessedAt": "2026-07-30T10:15:00"
  }
}
```

### Get analytics
- Method: GET
- Path: /api/urls/analytics
- Response:
```json
{
  "success": true,
  "message": "Analytics retrieved successfully",
  "data": {
    "totalUrls": 5,
    "activeUrls": 3,
    "expiredUrls": 2,
    "mostClicked": [
      {
        "shortCode": "abc123",
        "clickCount": 10
      }
    ]
  }
}
```

## Error responses
- 400 Bad Request for invalid input
- 404 Not Found for missing short URLs or IDs
