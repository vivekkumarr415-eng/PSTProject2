package com.example.urlshortener.repository;

import com.example.urlshortener.entity.ShortUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrlEntity, Long> {
    boolean existsByShortCode(String shortCode);

    Optional<ShortUrlEntity> findByShortCode(String shortCode);

    List<ShortUrlEntity> findAllByOrderByClickCountDesc();

    long countByExpiresAtIsNullOrExpiresAtAfter(LocalDateTime now);

    @Query("select e from ShortUrlEntity e where e.expiresAt is null or e.expiresAt >= ?1 order by e.clickCount desc")
    List<ShortUrlEntity> findActiveOrderByClickCountDesc(LocalDateTime now);
}
