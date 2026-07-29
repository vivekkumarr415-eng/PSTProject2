package com.example.urlshortener.mapper;

import com.example.urlshortener.dto.CreateShortUrlResponse;
import com.example.urlshortener.entity.ShortUrlEntity;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlMapper {

    public CreateShortUrlResponse toResponse(ShortUrlEntity entity, String baseUrl) {
        CreateShortUrlResponse response = new CreateShortUrlResponse();
        response.setShortCode(entity.getShortCode());
        response.setShortUrl(baseUrl + "/" + entity.getShortCode());
        return response;
    }
}
