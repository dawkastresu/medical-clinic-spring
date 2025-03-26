package com.dawkastresu.medicalclinic;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MedicalClinicException extends RuntimeException {

    private HttpStatus httpStatus;

    public MedicalClinicException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
