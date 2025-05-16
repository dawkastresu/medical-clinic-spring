package com.dawkastresu.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class AppointmentNotFoundException extends MedicalClinicException{

    public AppointmentNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

}
