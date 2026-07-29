package com.example.urlshortener.service.impl;

import com.example.urlshortener.dto.AnalyticsResponse;
import com.example.urlshortener.dto.CreateUrlRequest;
import com.example.urlshortener.dto.CreateUrlResponse;
import com.example.urlshortener.dto.RedirectUrlResponse;
import com.example.urlshortener.dto.ShortUrlSummaryResponse;
import com.example.urlshortener.entity.ShortUrlEntity;
import com.example.urlshortener.exception.InvalidUrlException;
import com.example.urlshortener.exception.ShortUrlNotFoundException;
import com.example.urlshortener.mapper.ShortUrlMapper;
import com.example.urlshortener.repository.ShortUrlRepository;
import com.example.urlshortener.service.ShortUrlService;
import com.example.urlshortener.util.ShortCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShortUrlServiceImpl implements ShortUrlService {

    private final ShortUrlRepository shortUrlRepository;
    private final ShortCodeGenerator shortCodeGenerator;
    private final ShortUrlMapper shortUrlMapper;

    @Override
    public CreateUrlResponse create(CreateUrlRequest request, String baseUrl) {
        validateRequest(request);

        String shortCode = generateUniqueShortCode();
        ShortUrlEntity entity = new ShortUrlEntity();
        entity.setOriginalUrl(request.getOriginalUrl());
        entity.setShortCode(shortCode);

        ShortUrlEntity savedEntity = shortUrlRepository.save(entity);
        return shortUrlMapper.toResponse(savedEntity, baseUrl);
    }

    @Override
    public RedirectUrlResponse resolve(String shortCode) {
        if (shortCode == null || shortCode.isBlank()) {
            throw new InvalidUrlException("Short code is required");
        }

        ShortUrlEntity entity = shortUrlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new ShortUrlNotFoundException("Short URL not found"));

        if (entity.getExpiresAt() != null && entity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ShortUrlNotFoundException("Short URL has expired");
        }

        entity.setClickCount(entity.getClickCount() + 1);
        entity.setLastAccessedAt(LocalDateTime.now());
        shortUrlRepository.save(entity);

        RedirectUrlResponse response = new RedirectUrlResponse();
        response.setOriginalUrl(entity.getOriginalUrl());
        response.setClickCount(entity.getClickCount());
        response.setLastAccessedAt(entity.getLastAccessedAt().toString());
        return response;
    }

    @Override
    public List<ShortUrlSummaryResponse> listAll(String baseUrl) {
        return shortUrlRepository.findAll().stream()
                .map(entity -> shortUrlMapper.toSummary(entity, baseUrl))
                .collect(Collectors.toList());
    }

    @Override
    public ShortUrlSummaryResponse getById(Long id, String baseUrl) {
        ShortUrlEntity entity = shortUrlRepository.findById(id)
                .orElseThrow(() -> new ShortUrlNotFoundException("Short URL not found"));
        return shortUrlMapper.toSummary(entity, baseUrl);
    }

    @Override
    public void deleteById(Long id) {
        ShortUrlEntity entity = shortUrlRepository.findById(id)
                .orElseThrow(() -> new ShortUrlNotFoundException("Short URL not found"));
        shortUrlRepository.delete(entity);
    }

    @Override
    public AnalyticsResponse getAnalytics(String baseUrl) {
        LocalDateTime now = LocalDateTime.now();
        long totalUrls = shortUrlRepository.count();
        long activeUrls = shortUrlRepository.countByExpiresAtIsNullOrExpiresAtAfter(now);
        long expiredUrls = totalUrls - activeUrls;

        AnalyticsResponse response = new AnalyticsResponse();
        response.setTotalUrls(totalUrls);
        response.setActiveUrls(activeUrls);
        response.setExpiredUrls(expiredUrls);
        response.setMostClicked(shortUrlRepository.findActiveOrderByClickCountDesc(now).stream()
                .limit(5)
                .map(entity -> shortUrlMapper.toSummary(entity, baseUrl))
                .collect(Collectors.toList()));
        return response;
    }

    private void validateRequest(CreateUrlRequest request) {
        if (request == null || Objects.isNull(request.getOriginalUrl()) || request.getOriginalUrl().isBlank()) {
            throw new InvalidUrlException("Original URL is required");
        }

        try {
            URI.create(request.getOriginalUrl()).toURL();
        } catch (Exception ex) {
            throw new InvalidUrlException("Original URL must be a valid URL");
        }
    }

    private String generateUniqueShortCode() {
        String shortCode;
        do {
            shortCode = shortCodeGenerator.generate();
        } while (shortUrlRepository.existsByShortCode(shortCode));
        return shortCode;
    }
}
