package com.dawkastresu.medicalclinic;

import org.springframework.http.HttpStatus;

public class InvalidUserDataException extends MedicalClinicException{

    private HttpStatus httpStatus;

    public InvalidUserDataException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
