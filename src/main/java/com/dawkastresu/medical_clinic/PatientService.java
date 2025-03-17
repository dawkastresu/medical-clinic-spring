package com.dawkastresu.medical_clinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
//ReauiredArgsConstruktor generuje konstruktor który przyjmuje pola finalne
@RequiredArgsConstructor
//Adnotacja Service mówi że ta klasa jest beanem zawierajacym logike biznesową aplikacji
@Service
public class PatientService {
    //PatientRepository również musi być beanem aby Spring mógł dostarczyć instancję tej klasy
    private final PatientRepository patientRepository;

    public void editPatientByEmail(String email, Patient newPatient) {
        Patient patient = patientRepository.findPatientByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        patient.setEmail(newPatient.getEmail());
        patient.setPassword(newPatient.getPassword());
        patient.setIdCardNo(newPatient.getIdCardNo());
        patient.setFirstName(newPatient.getFirstName());
        patient.setLastName(newPatient.getLastName());
        patient.setPhoneNumber(newPatient.getPhoneNumber());
        patient.setBirthday(newPatient.getBirthday());
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient findPatientByName(String email) {
        return patientRepository.findPatientByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
    }

    public void addNewPatient(Patient patient) {
        patientRepository.addPatient(patient);
    }

    public void removePatientByMail(String email) {
        patientRepository.removePatientByEmail(email);
    }

}
