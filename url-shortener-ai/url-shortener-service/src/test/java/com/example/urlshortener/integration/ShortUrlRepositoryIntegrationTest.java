package com.example.urlshortener.integration;

import com.example.urlshortener.entity.ShortUrlEntity;
import com.example.urlshortener.repository.ShortUrlRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ShortUrlRepositoryIntegrationTest {

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Test
    void shouldPersistAndRetrieveShortUrl() {
        ShortUrlEntity entity = new ShortUrlEntity();
        entity.setShortCode("abc123");
        entity.setOriginalUrl("https://example.com");
        entity.setClickCount(0);
        entity.setExpiresAt(LocalDateTime.now().plusDays(1));

        ShortUrlEntity saved = shortUrlRepository.save(entity);
        Optional<ShortUrlEntity> found = shortUrlRepository.findByShortCode("abc123");

        assertThat(saved.getId()).isNotNull();
        assertThat(found).isPresent();
        assertThat(found.get().getOriginalUrl()).isEqualTo("https://example.com");
    }

    @Test
    void shouldCountActiveAndExpiredUrls() {
        ShortUrlEntity active = new ShortUrlEntity();
        active.setShortCode("active1");
        active.setOriginalUrl("https://active.com");
        active.setClickCount(2);
        active.setExpiresAt(LocalDateTime.now().plusDays(1));

        ShortUrlEntity expired = new ShortUrlEntity();
        expired.setShortCode("expired1");
        expired.setOriginalUrl("https://expired.com");
        expired.setClickCount(1);
        expired.setExpiresAt(LocalDateTime.now().minusDays(1));

        shortUrlRepository.save(active);
        shortUrlRepository.save(expired);

        long activeCount = shortUrlRepository.countByExpiresAtIsNullOrExpiresAtAfter(LocalDateTime.now());

        assertThat(activeCount).isEqualTo(1L);
    }
}
