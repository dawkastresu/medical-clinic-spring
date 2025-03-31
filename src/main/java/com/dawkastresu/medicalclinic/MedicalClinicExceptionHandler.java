package com.dawkastresu.medicalclinic;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MedicalClinicExceptionHandler {

    @ExceptionHandler(PatientNotFoundException.class)
    protected ResponseEntity<ErrorMessage> handlePatientNotFoundException(PatientNotFoundException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), ex.getHttpStatus()), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler(InvalidPatientDataException.class)
    protected ResponseEntity<ErrorMessage> handleInvalidPatientDataException(InvalidPatientDataException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), ex.getHttpStatus()), new HttpHeaders(), ex.getHttpStatus());
    }

}
