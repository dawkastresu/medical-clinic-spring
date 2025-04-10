package com.dawkastresu.medicalclinic;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends MedicalClinicException{

    public UserNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

}
