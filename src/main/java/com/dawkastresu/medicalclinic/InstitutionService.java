package com.dawkastresu.medicalclinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstitutionService {

    private final InstitutionRepository repository;

    public List<Institution> getAll(){
        return repository.findAll();
    }

    public void addNew(Institution institution) {
        if (InstitutionValidator.validateInstitution(repository, institution.getName())) {
            repository.add(institution);
        }
    }

    public void remove(String name){
        repository.remove(name);
    }
}
