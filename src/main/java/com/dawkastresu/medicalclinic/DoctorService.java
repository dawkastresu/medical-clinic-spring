package com.dawkastresu.medicalclinic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            repository.save(doctor);
        }
        return  mapper.toDto(doctor);
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
        DoctorDto doctorDto = findById(id);
        Doctor newDoctor = mapper.toEntityFromDto(doctorDto);
        DoctorValidator.newValueNotNullValidate(newDoctor);
        DoctorValidator.validateDoctorEdit(newDoctor);
        Doctor doctor = repository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found", HttpStatus.NOT_FOUND));

        doctor.setInstitutions(institutionRepository.findAllById(command.getInstitutionIds()));
        doctor.update(newDoctor);
        repository.save(doctor);

        return mapper.toDto(newDoctor);
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
