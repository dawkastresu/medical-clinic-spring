package com.dawkastresu.medicalclinic.exception;

import com.dawkastresu.medicalclinic.dto.AppointmentDto;
import com.dawkastresu.medicalclinic.model.Appointment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//    @ExceptionHandler(InvalidAppointmentDataException.class)
//    protected ResponseEntity<ErrorMessage> handleInvalidAppointmentDataException(InvalidAppointmentDataException ex) {
//        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), ex.getHttpStatus()), new HttpHeaders(), ex.getHttpStatus());
//    }

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

    @ExceptionHandler(InstitutionNotFoundException.class)
    protected ResponseEntity<ErrorMessage> handleInstitutionNotFoundException(InstitutionNotFoundException ex) {
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), ex.getHttpStatus()), new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler(InvalidAppointmentDataException.class)
    public ResponseEntity<Object> handleInvalidAppointment(InvalidAppointmentDataException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("status", ex.getHttpStatus());

        List<AppointmentDto> overlapping = ex.getOverlappingAppointments();
        if (overlapping != null) {
            body.put("overlappingAppointments", overlapping);
        }

        return new ResponseEntity<>(body, ex.getHttpStatus());
    }

}
