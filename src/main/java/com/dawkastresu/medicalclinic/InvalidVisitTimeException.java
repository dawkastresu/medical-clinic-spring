package com.dawkastresu.medicalclinic;

import org.springframework.http.HttpStatus;

public class InvalidVisitTimeException extends MedicalClinicException{

    private HttpStatus httpStatus;

    public InvalidVisitTimeException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

}
