package com.dawkastresu.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class InstitutionNotFoundException extends MedicalClinicException {
    public InstitutionNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
