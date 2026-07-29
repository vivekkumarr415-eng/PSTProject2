package com.example.urlshortener.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortUrlSummaryResponse {
    private Long id;
    private String shortCode;
    private String originalUrl;
    private String shortUrl;
    private Integer clickCount;
    private String lastAccessedAt;
    private String expiresAt;
}
