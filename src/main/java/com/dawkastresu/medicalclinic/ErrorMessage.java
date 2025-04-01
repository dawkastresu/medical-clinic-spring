package com.dawkastresu.medicalclinic;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorMessage {

    private final String message;
    private final HttpStatus httpStatus;
    private final LocalDateTime occuredAt = LocalDateTime.now();

}
