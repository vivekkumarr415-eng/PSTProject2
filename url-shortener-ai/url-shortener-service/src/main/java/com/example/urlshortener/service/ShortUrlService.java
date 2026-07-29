package com.example.urlshortener.service;

import com.example.urlshortener.dto.AnalyticsResponse;
import com.example.urlshortener.dto.CreateUrlRequest;
import com.example.urlshortener.dto.CreateUrlResponse;
import com.example.urlshortener.dto.RedirectUrlResponse;
import com.example.urlshortener.dto.ShortUrlSummaryResponse;

import java.util.List;

public interface ShortUrlService {
    CreateUrlResponse create(CreateUrlRequest request, String baseUrl);

    RedirectUrlResponse resolve(String shortCode);

    List<ShortUrlSummaryResponse> listAll(String baseUrl);

    ShortUrlSummaryResponse getById(Long id, String baseUrl);

    void deleteById(Long id);

    AnalyticsResponse getAnalytics(String baseUrl);
}
