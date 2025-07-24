package com.dawkastresu.medicalclinic.exception;

import com.dawkastresu.medicalclinic.dto.AppointmentDto;
import com.dawkastresu.medicalclinic.model.Appointment;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class InvalidAppointmentDataException extends MedicalClinicException{

    private List<AppointmentDto> overlappingAppointments;

    public InvalidAppointmentDataException(String message, HttpStatus httpStatus, List<AppointmentDto> overlappingAppointments) {
        super(message, httpStatus);
        this.overlappingAppointments = overlappingAppointments;
    }

    public InvalidAppointmentDataException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
        this.overlappingAppointments = null;
    }

}
