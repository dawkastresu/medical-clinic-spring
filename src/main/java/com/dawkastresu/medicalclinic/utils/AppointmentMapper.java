package com.dawkastresu.medicalclinic.utils;

import com.dawkastresu.medicalclinic.command.CreateAppointmentCommand;
import com.dawkastresu.medicalclinic.dto.AppointmentDto;
import com.dawkastresu.medicalclinic.model.Appointment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    AppointmentDto toDto(Appointment appointment);
    Appointment toEntity(CreateAppointmentCommand command);

}
