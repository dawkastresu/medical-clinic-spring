package com.dawkastresu.medicalclinic.service;

import com.dawkastresu.medicalclinic.command.CreateInstitutionCommand;
import com.dawkastresu.medicalclinic.exception.InstitutionNotFoundException;
import com.dawkastresu.medicalclinic.utils.InstitutionMapper;
import com.dawkastresu.medicalclinic.repository.InstitutionRepository;
import com.dawkastresu.medicalclinic.utils.InstitutionValidator;
import com.dawkastresu.medicalclinic.dto.InstitutionDto;
import com.dawkastresu.medicalclinic.model.Institution;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstitutionService {

    private final InstitutionRepository repository;
    private final InstitutionMapper mapper;

    public Page<InstitutionDto> getAll(Pageable pageable) {
        Page<Institution> page = repository.findAll(pageable);
        return page.map(mapper::toDto);
    }

    @Transactional
    public InstitutionDto addNew(CreateInstitutionCommand createInstitutionCommand) {
        Institution institution = mapper.toEntity(createInstitutionCommand);
        if (InstitutionValidator.validateInstitution(repository, institution.getName())) {
            repository.save(institution);
        }
        return mapper.toDto(institution);
    }

    @Transactional
    public void remove(String name){
        repository.deleteByName(name);
    }

}
