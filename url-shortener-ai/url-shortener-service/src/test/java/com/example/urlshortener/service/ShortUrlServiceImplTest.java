package com.example.urlshortener.service;

import com.example.urlshortener.dto.CreateUrlRequest;
import com.example.urlshortener.dto.CreateUrlResponse;
import com.example.urlshortener.entity.ShortUrlEntity;
import com.example.urlshortener.exception.InvalidUrlException;
import com.example.urlshortener.mapper.ShortUrlMapper;
import com.example.urlshortener.repository.ShortUrlRepository;
import com.example.urlshortener.service.impl.ShortUrlServiceImpl;
import com.example.urlshortener.util.ShortCodeGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShortUrlServiceImplTest {

    @Mock
    private ShortUrlRepository shortUrlRepository;

    @Mock
    private ShortCodeGenerator shortCodeGenerator;

    @Mock
    private ShortUrlMapper shortUrlMapper;

    @InjectMocks
    private ShortUrlServiceImpl shortUrlService;

    @Test
    void shouldCreateShortUrlWhenRequestIsValid() {
        CreateUrlRequest request = new CreateUrlRequest();
        request.setOriginalUrl("https://example.com");

        ShortUrlEntity entity = new ShortUrlEntity();
        entity.setShortCode("abc123");
        entity.setOriginalUrl("https://example.com");

        CreateUrlResponse response = new CreateUrlResponse();
        response.setShortCode("abc123");
        response.setShortUrl("http://localhost/abc123");

        when(shortCodeGenerator.generate()).thenReturn("abc123");
        when(shortUrlRepository.existsByShortCode("abc123")).thenReturn(false);
        when(shortUrlRepository.save(any(ShortUrlEntity.class))).thenReturn(entity);
        when(shortUrlMapper.toResponse(entity, "http://localhost")).thenReturn(response);

        CreateUrlResponse result = shortUrlService.create(request, "http://localhost");

        assertThat(result.getShortCode()).isEqualTo("abc123");
        assertThat(result.getShortUrl()).isEqualTo("http://localhost/abc123");
        verify(shortUrlRepository).save(any(ShortUrlEntity.class));
    }

    @Test
    void shouldThrowInvalidUrlExceptionForMalformedUrl() {
        CreateUrlRequest request = new CreateUrlRequest();
        request.setOriginalUrl("not-a-valid-url");

        assertThrows(InvalidUrlException.class, () -> shortUrlService.create(request, "http://localhost"));
    }
}
