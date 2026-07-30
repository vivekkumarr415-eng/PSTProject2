package com.example.urlshortener.controller;

import com.example.urlshortener.config.ApiKeyFilter;
import com.example.urlshortener.config.SecurityProperties;
import com.example.urlshortener.dto.AnalyticsResponse;
import com.example.urlshortener.dto.CreateUrlRequest;
import com.example.urlshortener.dto.CreateUrlResponse;
import com.example.urlshortener.dto.RedirectUrlResponse;
import com.example.urlshortener.dto.ShortUrlSummaryResponse;
import com.example.urlshortener.service.ShortUrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShortUrlController.class)
@AutoConfigureMockMvc(addFilters = false)
class ShortUrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShortUrlService shortUrlService;

    @MockBean
    private ApiKeyFilter apiKeyFilter;

    @MockBean
    private SecurityProperties securityProperties;

    @Test
    void shouldCreateShortUrl() throws Exception {
        CreateUrlResponse response = new CreateUrlResponse();
        response.setShortCode("abc123");
        response.setShortUrl("http://localhost/abc123");

        when(shortUrlService.create(any(CreateUrlRequest.class), any(String.class))).thenReturn(response);

        mockMvc.perform(post("/api/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"originalUrl\":\"https://example.com\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.shortCode").value("abc123"));
    }

    @Test
    void shouldResolveShortUrl() throws Exception {
        RedirectUrlResponse response = new RedirectUrlResponse();
        response.setOriginalUrl("https://example.com");
        response.setClickCount(1);
        response.setLastAccessedAt("2026-07-30T00:00:00");

        when(shortUrlService.resolve("abc123")).thenReturn(response);

        mockMvc.perform(get("/api/urls/resolve/abc123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.originalUrl").value("https://example.com"));
    }

    @Test
    void shouldListUrls() throws Exception {
        ShortUrlSummaryResponse entry = new ShortUrlSummaryResponse();
        entry.setShortCode("abc123");
        entry.setOriginalUrl("https://example.com");

        when(shortUrlService.listAll(any(String.class))).thenReturn(List.of(entry));

        mockMvc.perform(get("/api/urls"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].shortCode").value("abc123"));
    }

    @Test
    void shouldGetUrlById() throws Exception {
        ShortUrlSummaryResponse entry = new ShortUrlSummaryResponse();
        entry.setId(1L);
        entry.setShortCode("abc123");
        entry.setOriginalUrl("https://example.com");

        when(shortUrlService.getById(1L, "http://localhost:8080")).thenReturn(entry);

        mockMvc.perform(get("/api/urls/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.shortCode").value("abc123"));
    }

    @Test
    void shouldDeleteUrl() throws Exception {
        doNothing().when(shortUrlService).deleteById(1L);

        mockMvc.perform(delete("/api/urls/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void shouldGetAnalytics() throws Exception {
        AnalyticsResponse response = new AnalyticsResponse();
        response.setTotalUrls(3);
        response.setActiveUrls(2);
        response.setExpiredUrls(1);
        response.setMostClicked(List.of());

        when(shortUrlService.getAnalytics(any(String.class))).thenReturn(response);

        mockMvc.perform(get("/api/urls/analytics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.totalUrls").value(3))
                .andExpect(jsonPath("$.data.activeUrls").value(2));
    }
}
