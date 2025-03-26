package com.dawkastresu.medicalclinic;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InvalidPatientDataException extends MedicalClinicException{

    private HttpStatus httpStatus;

    public InvalidPatientDataException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

}
