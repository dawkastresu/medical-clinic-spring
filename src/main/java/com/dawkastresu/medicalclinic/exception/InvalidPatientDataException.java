package com.dawkastresu.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class InvalidPatientDataException extends MedicalClinicException{

    private HttpStatus httpStatus;

    public InvalidPatientDataException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

}
