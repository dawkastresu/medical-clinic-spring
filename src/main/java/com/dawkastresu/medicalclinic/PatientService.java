package com.dawkastresu.medicalclinic;

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

        return mapper.toDto(newPatient);
    }

    public void editPasswordByMail(String email, PatientPassword password) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found", HttpStatus.NOT_FOUND));
        patient.setPassword(password.getPassword());
    }

    public List<PatientDto> getAll() {
        return patientRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public PatientDto findPatientByName(String email) {
        return patientRepository.findByEmail(email)
                .map(mapper::toDto)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found", HttpStatus.NOT_FOUND));
    }

    public PatientDto addNew(CreatePatientCommand createPatientCommand) {
        Patient patient = mapper.toEntity(createPatientCommand);
        PatientValidator.validatePatient(patient, patientRepository);
        patientRepository.add(patient);
        return mapper.toDto(patient);
    }

    public void removeByMail(String email) {
        patientRepository.removeByEmail(email);
    }

}
