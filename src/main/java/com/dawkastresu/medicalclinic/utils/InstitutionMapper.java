package com.dawkastresu.medicalclinic.utils;

import com.dawkastresu.medicalclinic.command.CreateInstitutionCommand;
import com.dawkastresu.medicalclinic.dto.InstitutionDto;
import com.dawkastresu.medicalclinic.model.Institution;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {

    Institution toEntity(CreateInstitutionCommand command);
    InstitutionDto toDto(Institution institution);

}