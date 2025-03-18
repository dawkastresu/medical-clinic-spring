package com.dawkastresu.medicalclinic;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PatientValidator {

    public static void validateEmail(String email, PatientRepository repository) {
        Optional<Patient> patientByEmail = repository.findByEmail(email);
        if (patientByEmail.isPresent()) {
            throw new IllegalArgumentException("Patient with given mail exists.");
        }
    }

    public static void validatePatient(Patient patient, PatientRepository repository) {
        validateEmail(patient.getEmail(), repository);
        if (patient.getEmail() == null || patient.getIdCardNo() == null) {
            throw new IllegalArgumentException("Patient email is null");
        }
    }



}
