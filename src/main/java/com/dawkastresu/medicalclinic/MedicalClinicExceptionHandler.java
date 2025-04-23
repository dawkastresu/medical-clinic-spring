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

    @ExceptionHandler(InvalidVisitTimeException.class)
    protected ResponseEntity<ErrorMessage> handleInvalidVisitTimeException(InvalidVisitTimeException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), ex.getHttpStatus()), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler(InvalidAppointmentData.class)
    protected ResponseEntity<ErrorMessage> handleInvalidAppointmentData(InvalidAppointmentData ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), ex.getHttpStatus()), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler(InvalidDoctorDataException.class)
    protected ResponseEntity<ErrorMessage> handleInvalidDoctorDataException(InvalidDoctorDataException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), ex.getHttpStatus()), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler(InvalidUserDataException.class)
    protected ResponseEntity<ErrorMessage> handleInvalidUserDataException(InvalidUserDataException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), ex.getHttpStatus()), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler(DoctorNotFoundException.class)
    protected ResponseEntity<ErrorMessage> handleDoctorNotFoundException(DoctorNotFoundException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), ex.getHttpStatus()), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), ex.getHttpStatus()), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    protected ResponseEntity<ErrorMessage> handleAppointmentNotFoundException(AppointmentNotFoundException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), ex.getHttpStatus()), new HttpHeaders(), ex.getHttpStatus());
    }

}
