package com.dawkastresu.medicalclinic;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class InvalidAppointmentData extends MedicalClinicException{

    private HttpStatus httpStatus;

    public InvalidAppointmentData(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

}
