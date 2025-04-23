package com.dawkastresu.medicalclinic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public final class AppointmentValidator {


    public static boolean validateAddPatientToAppointment(AppointmentRepository repository, Appointment appointment) {
        if (appointment.getPatientId() != null) {
            return false;
        } else return true;
    }

    public static void validateAppointment(AppointmentRepository repository, Appointment appointment) {
        if (appointment.getStartTime().getMinute() % 15 != 0 || appointment.getEndTime().getMinute() % 15 != 0) {
            throw new InvalidVisitTimeException("Appointment can only be booked for full quarters of an hour", HttpStatus.BAD_REQUEST);
        }
        if (appointment.getStartTime().isBefore(LocalDateTime.now())) {
            throw new InvalidVisitTimeException("It is not possible to register for a past visit", HttpStatus.BAD_REQUEST);
        }
        List<Appointment> appointments = repository.findAll();
        appointments.stream().forEach(app -> {
            if (app.getStartTime().equals(appointment.getStartTime()) && app.getDoctor().getId().equals(appointment.getDoctor().getId())) {
                throw new InvalidAppointmentData("There is already a appointment for this doctor at this time", HttpStatus.BAD_REQUEST);
            }
        });
    }



}
