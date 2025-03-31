package com.dawkastresu.medicalclinic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

//ReauiredArgsConstruktor generuje konstruktor który przyjmuje pola finalne
@RequiredArgsConstructor
//Adnotacja Service mówi że ta klasa jest beanem zawierajacym logike biznesową aplikacji
@Service
public class PatientService {
    //PatientRepository również musi być beanem aby Spring mógł dostarczyć instancję tej klasy
    private final PatientRepository patientRepository;

    public PatientDto editByEmail(String email, CreatePatientCommand createPatientCommand) {
        Patient newPatient = PatientMapper.mapToPatient(createPatientCommand);
        PatientValidator.newValueNotNullValidate(newPatient);
        PatientValidator.validatePatientEdit(newPatient);
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found", HttpStatus.NOT_FOUND));

        PatientValidator.cardIdNrNotChangedValidate(patient, newPatient);

        patient.setEmail(newPatient.getEmail());
        patient.setPassword(newPatient.getPassword());
        patient.setFirstName(newPatient.getFirstName());
        patient.setLastName(newPatient.getLastName());
        patient.setPhoneNumber(newPatient.getPhoneNumber());
        patient.setBirthday(newPatient.getBirthday());

        return PatientMapper.mapToDto(newPatient);
    }

    public void editPasswordByMail(String email, PatientPassword password) {
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found", HttpStatus.NOT_FOUND));
        patient.setPassword(password.getPassword());
    }

    public List<PatientDto> getAll() {
        return patientRepository.findAll().stream()
                .map(PatientMapper::mapToDto)
                .toList();
    }

    public PatientDto findPatientByName(String email) {
        return patientRepository.findByEmail(email)
                .map(PatientMapper::mapToDto)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found", HttpStatus.NOT_FOUND));
    }

    public Patient addNew(CreatePatientCommand createPatientCommand) {
        Patient patient = PatientMapper.mapToPatient(createPatientCommand);
        PatientValidator.validatePatient(patient, patientRepository);
        patientRepository.add(patient);
        return patient;
    }

    public void removeByMail(String email) {
        patientRepository.removeByEmail(email);
    }

}
