package com.outbrain.rss.controller;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiError {
    private LocalDateTime localDateTime;
    private HttpStatus status;
    private String message;

    ApiError(HttpStatus status, String message){
        this.localDateTime = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
