package com.example.urlshortener.service;

import com.example.urlshortener.dto.RedirectUrlResponse;
import com.example.urlshortener.entity.ShortUrlEntity;
import com.example.urlshortener.exception.ShortUrlNotFoundException;
import com.example.urlshortener.repository.ShortUrlRepository;
import com.example.urlshortener.service.impl.ShortUrlServiceImpl;
import com.example.urlshortener.util.ShortCodeGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShortUrlServiceResolveTest {

    @Mock
    private ShortUrlRepository shortUrlRepository;

    @Mock
    private ShortCodeGenerator shortCodeGenerator;

    @Mock
    private com.example.urlshortener.mapper.ShortUrlMapper shortUrlMapper;

    @InjectMocks
    private ShortUrlServiceImpl shortUrlService;

    @Test
    void shouldResolveExistingShortUrlAndUpdateMetadata() {
        ShortUrlEntity entity = new ShortUrlEntity();
        entity.setShortCode("abc123");
        entity.setOriginalUrl("https://example.com");
        entity.setClickCount(0);

        when(shortUrlRepository.findByShortCode("abc123")).thenReturn(Optional.of(entity));
        when(shortUrlRepository.save(any(ShortUrlEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RedirectUrlResponse response = shortUrlService.resolve("abc123");

        assertThat(response.getOriginalUrl()).isEqualTo("https://example.com");
        assertThat(response.getClickCount()).isEqualTo(1);
        verify(shortUrlRepository).save(any(ShortUrlEntity.class));
    }

    @Test
    void shouldThrowWhenShortUrlDoesNotExist() {
        when(shortUrlRepository.findByShortCode("missing")).thenReturn(Optional.empty());

        assertThrows(ShortUrlNotFoundException.class, () -> shortUrlService.resolve("missing"));
    }
}
