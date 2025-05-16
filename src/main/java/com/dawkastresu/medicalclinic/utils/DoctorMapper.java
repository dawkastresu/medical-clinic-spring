package com.dawkastresu.medicalclinic.utils;

import com.dawkastresu.medicalclinic.command.CreateDoctorCommand;
import com.dawkastresu.medicalclinic.dto.DoctorDto;
import com.dawkastresu.medicalclinic.model.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    Doctor toEntity(CreateDoctorCommand command);
    DoctorDto toDto(Doctor doctor);
    Doctor toEntityFromDto(DoctorDto dto);

}
