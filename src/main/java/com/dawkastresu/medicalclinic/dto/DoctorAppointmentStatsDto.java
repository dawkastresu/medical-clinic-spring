package com.dawkastresu.medicalclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class DoctorAppointmentStatsDto {

    private long totalAppointments;

    private long unassignedAppointments;

    private long attendedAppointments;

    private double attendanceRate;

    private Double averageRating;

}

