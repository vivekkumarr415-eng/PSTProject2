package com.example.urlshortener.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;
    private final Map<String, RequestCounter> requestCounters = new ConcurrentHashMap<>();

    public ApiKeyFilter(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestUri = request.getRequestURI();
        if (isOpenEndpoint(requestUri)) {
            filterChain.doFilter(request, response);
            return;
        }

        String apiKey = request.getHeader(securityProperties.getHeaderName());
        if (apiKey == null || !apiKey.equals(securityProperties.getValue())) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"success\":false,\"message\":\"Unauthorized\",\"data\":null}");
            return;
        }

        if (!allowRequest(request.getRemoteAddr())) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"success\":false,\"message\":\"Rate limit exceeded\",\"data\":null}");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isOpenEndpoint(String requestUri) {
        return requestUri.startsWith("/swagger-ui")
                || requestUri.startsWith("/v3/api-docs")
                || requestUri.startsWith("/actuator/health")
                || requestUri.startsWith("/actuator/info");
    }

    private boolean allowRequest(String remoteAddr) {
        RequestCounter counter = requestCounters.computeIfAbsent(remoteAddr, key -> new RequestCounter());
        return counter.tryIncrement(securityProperties.getRequestsPerMinute());
    }

    private static class RequestCounter {
        private Instant windowStart = Instant.now();
        private final AtomicInteger count = new AtomicInteger();

        boolean tryIncrement(int maxRequestsPerMinute) {
            Instant now = Instant.now();
            if (Duration.between(windowStart, now).toMinutes() >= 1) {
                windowStart = now;
                count.set(0);
            }
            return count.incrementAndGet() <= maxRequestsPerMinute;
        }
    }
}
