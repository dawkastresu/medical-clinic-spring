package com.dawkastresu.medicalclinic;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public final class InstitutionValidator {

    public static boolean validateInstitution(InstitutionRepository repository, String name){
        List<Institution> institutions = repository.findAll();
        return institutions.stream()
                .anyMatch(institution -> name.equalsIgnoreCase(institution.getName()));
    }
}
