package com.dawkastresu.medicalclinic.service;

import com.dawkastresu.medicalclinic.command.RegisterDoctorCommand;
import com.dawkastresu.medicalclinic.dto.DoctorDto;
import com.dawkastresu.medicalclinic.exception.DoctorNotFoundException;
import com.dawkastresu.medicalclinic.model.*;
import com.dawkastresu.medicalclinic.repository.DoctorRepository;
import com.dawkastresu.medicalclinic.repository.InstitutionRepository;
import com.dawkastresu.medicalclinic.utils.DoctorMapper;
import com.dawkastresu.medicalclinic.utils.DoctorValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository repository;
    private final InstitutionRepository institutionRepository;
    private final DoctorMapper mapper;

    public Page<DoctorDto> getAll(Pageable pageable) {
        Page<Doctor> page = repository.findAll(pageable);
        return page.map(mapper::toDto);
    }

    public Page<DoctorDto> getDoctorsBySpecialization(Specialization specialization, Pageable pageable) {
        return repository
                .findDoctorBySpecialization(specialization, pageable)
                .map(mapper::toDto);
    }

    @Transactional
    public DoctorDto addNew(RegisterDoctorCommand command) {
        Doctor doctor = Doctor.create(command);

        if (DoctorValidator.validateDoctor(repository, doctor.getFirstName())) {
            doctor = repository.save(doctor);
        } else {
            throw new IllegalArgumentException("Invalid doctor data");
        }

        return mapper.toDto(doctor);
    }


    @Transactional
    public void remove(Long id) {
        if (!repository.existsById(id)) {
            throw new DoctorNotFoundException("No doctor found with this id", HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
    }

    public DoctorDto findById(Long id) {
        Doctor doctor = repository.findById(id).
                orElseThrow(() -> new DoctorNotFoundException("Doctor not found", HttpStatus.NOT_FOUND));
        return mapper.toDto(doctor);
    }

    @Transactional
    public DoctorDto editById(Long id, RegisterDoctorCommand command) {
        Doctor doctor = repository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found", HttpStatus.NOT_FOUND));

        Doctor newDoctor = Doctor.create(command);
        DoctorValidator.newValueNotNullValidate(newDoctor);
        DoctorValidator.validateDoctorEdit(newDoctor);

        doctor.update(newDoctor);
        repository.save(doctor);

        return mapper.toDto(doctor);
    }

    public void editPasswordById(Long id, Password password) {
        Doctor doctor = repository.findById(id).orElseThrow(() -> new DoctorNotFoundException("Doctor not found", HttpStatus.NOT_FOUND));
        doctor.setPassword(password.getPassword());
    }

    public void addDoctorToInstitution(Long idDoctor, Long idInstitution) {
        Institution institution = institutionRepository.findById(idInstitution).orElseThrow(() -> new IllegalArgumentException());
        Doctor doctor = repository.findById(idDoctor).orElseThrow(() -> new IllegalArgumentException());
        doctor.getInstitutions().add(institution);
        institution.getDoctors().add(doctor);
        repository.save(doctor);
        institutionRepository.save(institution);
    }

}
