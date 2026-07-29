package com.example.urlshortener.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RedirectUrlResponse {
    private String originalUrl;
    private Integer clickCount;
    private String lastAccessedAt;
}
