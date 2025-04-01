package com.dawkastresu.medicalclinic;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    Patient toEntity(CreatePatientCommand patientCommand);

    @Mapping(source = ".", target = "fullName", qualifiedByName = "mapToFullName")
    PatientDto toDto(Patient patient);

    @Named("mapToFullName")
    default String toFullName(Patient patient) {
        return patient.getFirstName() + " " + patient.getLastName();
    }

}