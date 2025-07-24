package com.dawkastresu.medicalclinic.utils;

import com.dawkastresu.medicalclinic.dto.AppointmentRatingDto;
import com.dawkastresu.medicalclinic.model.AppointmentRating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentRatingMapper {

    @Mapping(source = "appointment.id", target = "appointmentId")
    @Mapping(source = "doctor.id", target = "doctorId")
    AppointmentRatingDto toDto(AppointmentRating appointmentRating);

    @Mapping(source = "appointmentId", target = "appointment.id")
    @Mapping(source = "doctorId", target = "doctor.id")
    AppointmentRating toEntity(AppointmentRatingDto appointmentRatingDto);

}
