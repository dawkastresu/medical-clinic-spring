package com.dawkastresu.medicalclinic.service;

import com.dawkastresu.medicalclinic.command.CreateInstitutionCommand;
import com.dawkastresu.medicalclinic.utils.InstitutionMapper;
import com.dawkastresu.medicalclinic.repository.InstitutionRepository;
import com.dawkastresu.medicalclinic.utils.InstitutionValidator;
import com.dawkastresu.medicalclinic.dto.InstitutionDto;
import com.dawkastresu.medicalclinic.model.Institution;
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
            repository.save(institution);
        }
        return mapper.toDto(institution);
    }

    public void remove(String name){
        repository.deleteByName(name);
    }

}
