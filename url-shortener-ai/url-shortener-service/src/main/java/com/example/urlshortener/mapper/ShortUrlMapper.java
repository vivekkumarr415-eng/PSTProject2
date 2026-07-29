package com.example.urlshortener.mapper;

import com.example.urlshortener.dto.CreateUrlResponse;
import com.example.urlshortener.entity.ShortUrlEntity;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlMapper {

    public CreateUrlResponse toResponse(ShortUrlEntity entity, String baseUrl) {
        CreateUrlResponse response = new CreateUrlResponse();
        response.setShortCode(entity.getShortCode());
        response.setShortUrl(baseUrl + "/" + entity.getShortCode());
        return response;
    }
}
