package com.dawkastresu.medicalclinic.utils;

import com.dawkastresu.medicalclinic.model.Institution;
import com.dawkastresu.medicalclinic.repository.InstitutionRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class InstitutionValidator {

    public static boolean validateInstitution(InstitutionRepository repository, String name){
        List<Institution> institutions = repository.findAll();
        return institutions.stream()
                .noneMatch(institution -> name.equalsIgnoreCase(institution.getName()));
    }

}
