package com.example.urlshortener.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateShortUrlResponse {
    private String shortCode;
    private String shortUrl;
}
