package com.dawkastresu.medicalclinic;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {

    Institution toEntity(CreateInstitutionCommand command);
    InstitutionDto toDto(Institution institution);

}