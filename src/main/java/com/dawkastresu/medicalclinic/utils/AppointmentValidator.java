package com.dawkastresu.medicalclinic.utils;

import com.dawkastresu.medicalclinic.dto.AppointmentDto;
import com.dawkastresu.medicalclinic.exception.InvalidAppointmentDataException;
import com.dawkastresu.medicalclinic.exception.InvalidAppointmentDataException;
import com.dawkastresu.medicalclinic.exception.InvalidVisitTimeException;
import com.dawkastresu.medicalclinic.model.Appointment;
import com.dawkastresu.medicalclinic.repository.AppointmentRepository;
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

    public static void validateAppointment(AppointmentRepository repository, Appointment appointment, AppointmentMapper mapper) {
        if (appointment.getStartTime().getMinute() % 15 != 0 || appointment.getEndTime().getMinute() % 15 != 0) {
            throw new InvalidVisitTimeException("Appointment can only be booked for full quarters of an hour", HttpStatus.BAD_REQUEST);
        }

        if (appointment.getStartTime().isBefore(LocalDateTime.now())) {
            throw new InvalidVisitTimeException("It is not possible to register for a past visit", HttpStatus.BAD_REQUEST);
        }

        List<Appointment> overlappingAppointments = repository.findOverlappingAppointments(
                appointment.getDoctor().getId(),
                appointment.getStartTime(),
                appointment.getEndTime()
        );

        if (!overlappingAppointments.isEmpty()) {
            List<AppointmentDto> overlappingDtos = overlappingAppointments.stream()
                    .map(mapper::toDto)
                    .toList(); // użyj .collect(Collectors.toList()) jeśli nie masz Javy 16+
            throw new InvalidAppointmentDataException(
                    "There is already an appointment for this doctor at this time",
                    HttpStatus.BAD_REQUEST,
                    overlappingDtos
            );
        }
    }



}
