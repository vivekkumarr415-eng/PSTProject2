package com.example.urlshortener.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUrlResponse {
    private String shortCode;
    private String shortUrl;
}
