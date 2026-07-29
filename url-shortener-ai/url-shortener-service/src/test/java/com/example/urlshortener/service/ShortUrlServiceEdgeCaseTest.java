package com.example.urlshortener.service;

import com.example.urlshortener.exception.InvalidUrlException;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShortUrlServiceEdgeCaseTest {

    @Mock
    private ShortUrlRepository shortUrlRepository;

    @Mock
    private ShortCodeGenerator shortCodeGenerator;

    @Mock
    private com.example.urlshortener.mapper.ShortUrlMapper shortUrlMapper;

    @InjectMocks
    private ShortUrlServiceImpl shortUrlService;

    @Test
    void shouldRejectNullRequest() {
        assertThrows(InvalidUrlException.class, () -> shortUrlService.create(null, "http://localhost"));
    }

    @Test
    void shouldRejectBlankShortCode() {
        assertThrows(InvalidUrlException.class, () -> shortUrlService.resolve("   "));
    }

    @Test
    void shouldThrowWhenDeletingMissingId() {
        when(shortUrlRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ShortUrlNotFoundException.class, () -> shortUrlService.deleteById(99L));
    }

    @Test
    void shouldThrowWhenGettingMissingId() {
        when(shortUrlRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ShortUrlNotFoundException.class, () -> shortUrlService.getById(99L, "http://localhost"));
    }
}
