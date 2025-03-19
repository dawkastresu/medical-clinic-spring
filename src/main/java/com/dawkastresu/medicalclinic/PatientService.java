package com.dawkastresu.medicalclinic;

import lombok.RequiredArgsConstructor;
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

    public void editByEmail(String email, Patient newPatient) {
        if (newPatient.getFirstName() == null || newPatient.getLastName() == null || newPatient.getPhoneNumber() == null || newPatient.getPassword() == null || newPatient.getBirthday() == null) {
            throw new IllegalArgumentException("You cannot change any patient data value to null.");
        }
        PatientValidator.validatePatient(newPatient, patientRepository);
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        if (!Objects.equals(newPatient.getIdCardNo(), patient.getIdCardNo())){
            throw new IllegalArgumentException("IdCardNo is different. You can't edit ID card number");
        }

        patient.setEmail(newPatient.getEmail());
        patient.setPassword(newPatient.getPassword());
        patient.setFirstName(newPatient.getFirstName());
        patient.setLastName(newPatient.getLastName());
        patient.setPhoneNumber(newPatient.getPhoneNumber());
        patient.setBirthday(newPatient.getBirthday());

    }

    public void editPasswordByMail(String email, String password) {

        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        patient.setPassword(password);
    }

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public Patient findPatientByName(String email) {
        return patientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
    }

    public void addNew(Patient patient) {
        PatientValidator.validatePatient(patient, patientRepository);
        patientRepository.add(patient);
    }

    public void removeByMail(String email) {
        patientRepository.removeByEmail(email);
    }

}
