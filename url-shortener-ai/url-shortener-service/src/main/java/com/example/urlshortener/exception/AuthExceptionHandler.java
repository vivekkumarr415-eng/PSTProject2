package com.example.urlshortener.exception;

import com.example.urlshortener.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler({BadCredentialsException.class, AccessDeniedException.class})
    public ResponseEntity<ApiResponse<String>> handleSecurityException(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(false, "Unauthorized", null));
    }
}
