package com.dawkastresu.medicalclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class DoctorAverageRatingDto {

    private Long doctorId;
    private Double avgRating;

}


