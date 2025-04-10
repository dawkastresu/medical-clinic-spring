package com.dawkastresu.medicalclinic;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    Doctor toEntity(CreateDoctorCommand command);
    DoctorDto toDto(Doctor doctor);

}
