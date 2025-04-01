package com.dawkastresu.medicalclinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstitutionService {

    private final InstitutionRepository repository;
    private final InstitutionMapper mapper;

    public List<InstitutionDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public InstitutionDto addNew(CreateInstitutionCommand createInstitutionCommand) {
        Institution institution = mapper.toEntity(createInstitutionCommand);
        if (InstitutionValidator.validateInstitution(repository, institution.getName())) {
            repository.add(institution);
        }
        return mapper.toDto(institution);
    }

    public void remove(String name){
        repository.remove(name);
    }

}
