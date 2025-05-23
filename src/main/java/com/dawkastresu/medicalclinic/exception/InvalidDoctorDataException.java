package com.dawkastresu.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class InvalidDoctorDataException extends MedicalClinicException {

    private HttpStatus httpStatus;

    public InvalidDoctorDataException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

}
