package com.example.urlshortener.controller;

import com.example.urlshortener.config.ApiKeyFilter;
import com.example.urlshortener.config.SecurityProperties;
import com.example.urlshortener.service.ShortUrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShortUrlController.class)
@AutoConfigureMockMvc(addFilters = false)
class ShortUrlControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShortUrlService shortUrlService;

    @MockBean
    private ApiKeyFilter apiKeyFilter;

    @MockBean
    private SecurityProperties securityProperties;

    @Test
    void shouldRejectBlankUrl() throws Exception {
        mockMvc.perform(post("/api/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"originalUrl\":\"   \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void shouldRejectMissingUrl() throws Exception {
        mockMvc.perform(post("/api/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }
}
