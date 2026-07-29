package com.example.urlshortener.controller;

import com.example.urlshortener.dto.ApiResponse;
import com.example.urlshortener.dto.CreateUrlRequest;
import com.example.urlshortener.dto.CreateUrlResponse;
import com.example.urlshortener.dto.RedirectUrlResponse;
import com.example.urlshortener.service.ShortUrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/urls")
@RequiredArgsConstructor
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateUrlResponse>> createUrl(
            @Valid @RequestBody CreateUrlRequest request,
            @RequestHeader(value = "X-Base-Url", required = false, defaultValue = "http://localhost:8080") String baseUrl) {
        CreateUrlResponse payload = shortUrlService.create(request, baseUrl);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "URL created successfully", payload));
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<ApiResponse<RedirectUrlResponse>> resolveUrl(@PathVariable String shortCode) {
        RedirectUrlResponse payload = shortUrlService.resolve(shortCode);
        return ResponseEntity.ok(new ApiResponse<>(true, "URL resolved successfully", payload));
    }
}
