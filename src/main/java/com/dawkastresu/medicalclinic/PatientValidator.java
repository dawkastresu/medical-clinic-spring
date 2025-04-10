package com.dawkastresu.medicalclinic;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PatientValidator {

    public static void validateEmail(String email, PatientRepository repository) {
        Optional<Patient> patientByEmail = repository.findByEmail(email);
        if (patientByEmail.isPresent()) {
            throw new InvalidPatientDataException("Patient with given mail exists.", HttpStatus.BAD_REQUEST);
        }
    }

    public static void validatePatient(Patient patient, PatientRepository repository) {
        validateEmail(patient.getEmail(), repository);
        if (patient.getEmail() == null || patient.getIdCardNo() == null) {
            throw new InvalidPatientDataException("Patient email is null", HttpStatus.BAD_REQUEST);
        }
    }

    public static void validatePatientEdit(Patient patient) {
        if (patient.getEmail() == null || patient.getIdCardNo() == null) {
            throw new InvalidPatientDataException("Patient email or idCardNo is null", HttpStatus.BAD_REQUEST);
        }
    }

    public static void newValueNotNullValidate(Patient newPatient){
        if (newPatient.getFirstName() == null || newPatient.getLastName() == null || newPatient.getPhoneNumber() == null || newPatient.getBirthday() == null) {
            throw new InvalidPatientDataException("You cannot change any patient data value to null.", HttpStatus.BAD_REQUEST);
        }
    }

    public static void cardIdNrNotChangedValidate(Patient patient, Patient newPatient) {
        if (!Objects.equals(newPatient.getIdCardNo(), patient.getIdCardNo())){
            throw new InvalidPatientDataException("IdCardNo is different. You can't edit ID card number", HttpStatus.BAD_REQUEST);
        }
    }

}
