package com.dawkastresu.medicalclinic;

public final class PatientMapper {

    public static PatientDto mapToDto(Patient patient) {
        return new PatientDto(
                patient.getEmail(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getPhoneNumber(),
                patient.getBirthday()
        );
    }

    public static Patient mapToPatient(CreatePatientCommand createPatientCommand) {
        return new Patient(
                createPatientCommand.getEmail(),
                createPatientCommand.getPassword(),
                createPatientCommand.getIdCardNo(),
                createPatientCommand.getFirstName(),
                createPatientCommand.getLastName(),
                createPatientCommand.getPhoneNumber(),
                createPatientCommand.getBirthday()
        );
    }

}
