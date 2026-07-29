package com.example.urlshortener.service;

import com.example.urlshortener.dto.CreateUrlRequest;
import com.example.urlshortener.dto.CreateUrlResponse;
import com.example.urlshortener.dto.RedirectUrlResponse;

public interface ShortUrlService {
    CreateUrlResponse create(CreateUrlRequest request, String baseUrl);

    RedirectUrlResponse resolve(String shortCode);
}
