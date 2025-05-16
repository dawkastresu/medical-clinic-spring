package com.dawkastresu.medicalclinic.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@JsonAutoDetect(fieldVisibility = ANY)
public class AppointmentDto {

    Long id;
    LocalDateTime startTime;
    LocalDateTime endTime;
    Long patientId;
    SimpleDoctorDto doctor;

}
