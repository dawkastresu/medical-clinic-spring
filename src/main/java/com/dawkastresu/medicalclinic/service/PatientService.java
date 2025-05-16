package com.dawkastresu.medicalclinic.service;

import com.dawkastresu.medicalclinic.command.CreatePatientCommand;
import com.dawkastresu.medicalclinic.command.RegisterPatientCommand;
import com.dawkastresu.medicalclinic.dto.PatientDto;
import com.dawkastresu.medicalclinic.exception.PatientNotFoundException;
import com.dawkastresu.medicalclinic.model.Patient;
import com.dawkastresu.medicalclinic.repository.PatientRepository;
import com.dawkastresu.medicalclinic.utils.PatientMapper;
import com.dawkastresu.medicalclinic.utils.PatientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

//ReauiredArgsConstruktor generuje konstruktor który przyjmuje pola finalne
@RequiredArgsConstructor
//Adnotacja Service mówi że ta klasa jest beanem zawierajacym logike biznesową aplikacji
@Service
public class PatientService {
    //PatientRepository również musi być beanem aby Spring mógł dostarczyć instancję tej klasy
    private final PatientRepository patientRepository;
    private final PatientMapper mapper;

    public PatientDto editByEmail(String email, CreatePatientCommand createPatientCommand) {
        Patient newPatient = mapper.toEntity(createPatientCommand);
        PatientValidator.newValueNotNullValidate(newPatient);
        PatientValidator.validatePatientEdit(newPatient);
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found", HttpStatus.NOT_FOUND));

        PatientValidator.cardIdNrNotChangedValidate(patient, newPatient);

        patient.update(newPatient);
        patientRepository.save(patient);

        return mapper.toDto(newPatient);
    }

    public List<PatientDto> getAll() {
        return patientRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public PatientDto findPatientByEmail(String email) {
        return patientRepository.findByEmail(email)
                .map(mapper::toDto)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found", HttpStatus.NOT_FOUND));
    }

    public PatientDto addNew(RegisterPatientCommand command) {
        Patient patient = Patient.create(command);
        PatientValidator.validatePatient(patient, patientRepository);
        Patient savedPatient = patientRepository.save(patient);
        return mapper.toDto(savedPatient);
    }

    public void removeByMail(String email) {
        patientRepository.deleteByEmail(email);
    }

}
