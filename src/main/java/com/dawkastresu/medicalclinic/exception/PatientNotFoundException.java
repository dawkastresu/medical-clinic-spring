package com.dawkastresu.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class PatientNotFoundException extends MedicalClinicException{

    public PatientNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

}
