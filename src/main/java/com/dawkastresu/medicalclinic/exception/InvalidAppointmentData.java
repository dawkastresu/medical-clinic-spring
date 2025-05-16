package com.dawkastresu.medicalclinic.exception;

import org.springframework.http.HttpStatus;

public class InvalidAppointmentData extends MedicalClinicException{

    private HttpStatus httpStatus;

    public InvalidAppointmentData(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

}
