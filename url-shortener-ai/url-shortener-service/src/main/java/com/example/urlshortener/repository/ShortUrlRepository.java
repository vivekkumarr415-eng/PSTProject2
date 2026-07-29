package com.example.urlshortener.repository;

import com.example.urlshortener.entity.ShortUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrlEntity, Long> {
    boolean existsByShortCode(String shortCode);

    Optional<ShortUrlEntity> findByShortCode(String shortCode);
}
