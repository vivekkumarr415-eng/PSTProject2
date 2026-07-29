package com.example.urlshortener.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnalyticsResponse {
    private long totalUrls;
    private long activeUrls;
    private long expiredUrls;
    private List<ShortUrlSummaryResponse> mostClicked;
}
