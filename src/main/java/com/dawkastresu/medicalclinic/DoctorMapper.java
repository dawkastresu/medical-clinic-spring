package com.dawkastresu.medicalclinic;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    Doctor toEntity(CreateDoctorCommand command);
    DoctorDto toDto(Doctor doctor);
    Doctor toEntityFromDto(DoctorDto dto);

}
