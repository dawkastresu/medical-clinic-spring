package com.dawkastresu.medicalclinic;

import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    AppointmentDto toDto(Appointment appointment);
    Appointment toEntity(CreateAppointmentCommand command);

}
