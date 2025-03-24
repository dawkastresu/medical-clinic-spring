package com.dawkastresu.medicalclinic;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;
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

    public static void newValueNotNullValidate(Patient newPatient){
        if (newPatient.getFirstName() == null || newPatient.getLastName() == null || newPatient.getPhoneNumber() == null || newPatient.getPassword() == null || newPatient.getBirthday() == null) {
            throw new IllegalArgumentException("You cannot change any patient data value to null.");
        }
    }

    public static void cardIdNrNotChangedValidate(Patient patient, Patient newPatient) {
        if (!Objects.equals(newPatient.getIdCardNo(), patient.getIdCardNo())){
            throw new IllegalArgumentException("IdCardNo is different. You can't edit ID card number");
        }
    }

}
