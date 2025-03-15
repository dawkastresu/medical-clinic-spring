package com.dawkastresu.medical_clinic;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public void editPatientByEmail(String email, Patient newPatient) {
        Patient patient = patientRepository.findPatientByEmail(email)
                .orElseThrow(IllegalArgumentException::new);

        patient.setEmail(newPatient.getEmail());
        patient.setPassword(newPatient.getPassword());
        patient.setIdCardNo(newPatient.getIdCardNo());
        patient.setFirstName(newPatient.getFirstName());
        patient.setLastName(newPatient.getLastName());
        patient.setPhoneNumber(newPatient.getPhoneNumber());
        patient.setBirthday(newPatient.getBirthday());

        patientRepository.removePatientByEmail(email);
        patientRepository.addPatient(patient);
    }
}
