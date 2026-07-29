package com.example.urlshortener.mapper;

import com.example.urlshortener.dto.CreateUrlResponse;
import com.example.urlshortener.dto.ShortUrlSummaryResponse;
import com.example.urlshortener.entity.ShortUrlEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ShortUrlMapper {

    public CreateUrlResponse toResponse(ShortUrlEntity entity, String baseUrl) {
        CreateUrlResponse response = new CreateUrlResponse();
        response.setShortCode(entity.getShortCode());
        response.setShortUrl(baseUrl + "/" + entity.getShortCode());
        return response;
    }

    public ShortUrlSummaryResponse toSummary(ShortUrlEntity entity, String baseUrl) {
        ShortUrlSummaryResponse response = new ShortUrlSummaryResponse();
        response.setId(entity.getId());
        response.setShortCode(entity.getShortCode());
        response.setOriginalUrl(entity.getOriginalUrl());
        response.setShortUrl(baseUrl + "/" + entity.getShortCode());
        response.setClickCount(entity.getClickCount());
        response.setLastAccessedAt(format(entity.getLastAccessedAt()));
        response.setExpiresAt(format(entity.getExpiresAt()));
        return response;
    }

    private String format(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.toString();
    }
}
