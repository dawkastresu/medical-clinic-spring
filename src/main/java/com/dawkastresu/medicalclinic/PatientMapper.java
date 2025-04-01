package com.dawkastresu.medicalclinic;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    Patient toEntity(CreatePatientCommand patientCommand);
    PatientDto toDto(Patient patient);

}
