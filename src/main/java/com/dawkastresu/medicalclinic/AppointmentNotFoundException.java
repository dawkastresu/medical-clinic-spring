package com.dawkastresu.medicalclinic;

import org.springframework.http.HttpStatus;

public class AppointmentNotFoundException extends MedicalClinicException{

    AppointmentNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

}
