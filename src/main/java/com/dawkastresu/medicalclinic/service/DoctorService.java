package com.dawkastresu.medicalclinic.service;

import com.dawkastresu.medicalclinic.command.RegisterDoctorCommand;
import com.dawkastresu.medicalclinic.dto.DoctorDto;
import com.dawkastresu.medicalclinic.exception.DoctorNotFoundException;
import com.dawkastresu.medicalclinic.model.Doctor;
import com.dawkastresu.medicalclinic.model.Institution;
import com.dawkastresu.medicalclinic.model.Password;
import com.dawkastresu.medicalclinic.repository.DoctorRepository;
import com.dawkastresu.medicalclinic.repository.InstitutionRepository;
import com.dawkastresu.medicalclinic.utils.DoctorMapper;
import com.dawkastresu.medicalclinic.utils.DoctorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository repository;
    private final InstitutionRepository institutionRepository;
    private final DoctorMapper mapper;

    public List<DoctorDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public DoctorDto addNew(RegisterDoctorCommand command) {
        Doctor doctor = Doctor.create(command);
        if (DoctorValidator.validateDoctor(repository, doctor.getFirstName())) {
            doctor = repository.save(doctor); // Przypisujemy zwrócony obiekt do zmiennej doctor
        }
        return mapper.toDto(doctor);
    }

    public void remove(Long id) {
        repository.deleteById(id);
    }

    public DoctorDto findById(Long id) {
        Doctor doctor = repository.findById(id).
                orElseThrow(() -> new DoctorNotFoundException("Doctor not found", HttpStatus.NOT_FOUND));
        return mapper.toDto(doctor);
    }

    public DoctorDto editById(Long id, RegisterDoctorCommand command) {
        Doctor doctor = repository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found", HttpStatus.NOT_FOUND));

        Doctor newDoctor = Doctor.create(command);
        DoctorValidator.newValueNotNullValidate(newDoctor);
        DoctorValidator.validateDoctorEdit(newDoctor);

        doctor.setInstitutions(institutionRepository.findAllById(command.getInstitutionIds()));
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
        institution.setDoctors(List.of(doctor));
    }

}
