package com.dawkastresu.medicalclinic;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorMessage {
    private String message;
    private HttpStatus httpStatus;
    private LocalDateTime occuredAt;

    public ErrorMessage(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.occuredAt = LocalDateTime.now();
    }
}
