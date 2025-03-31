package com.dawkastresu.medicalclinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstitutionService {

    private final InstitutionRepository repository;

    public List<InstitutionDto> getAll(){
        return repository.findAll().stream()
                .map(InstitutionMapper::maptoDto)
                .toList();
    }

    public InstitutionDto addNew(CreateInstitutionCommand createInstitutionCommand) {
        Institution institution = InstitutionMapper.mapToInstitution(createInstitutionCommand);
        if (InstitutionValidator.validateInstitution(repository, institution.getName())) {
            repository.add(institution);
        }
        return InstitutionMapper.maptoDto(institution);
    }

    public void remove(String name){
        repository.remove(name);
    }

}
