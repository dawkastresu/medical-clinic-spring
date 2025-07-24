package com.dawkastresu.medicalclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class AppointmentRatingDto {

    private final Long id;

    private final Long appointmentId;

    private final Long doctorId;

    private final Double ratingValue;

    private final LocalDateTime ratingDate;

}
