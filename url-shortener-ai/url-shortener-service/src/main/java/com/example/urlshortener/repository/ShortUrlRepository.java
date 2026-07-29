package com.example.urlshortener.repository;

import com.example.urlshortener.entity.ShortUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrlEntity, Long> {
}
