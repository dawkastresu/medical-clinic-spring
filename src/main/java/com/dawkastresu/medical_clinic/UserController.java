package com.dawkastresu.medical_clinic;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients/")
public class UserController {

    private final PatientRepository patientRepository;
    private final PatientService patientService;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @GetMapping("/{email}")
    public Optional<Patient> getPatientByMail(@PathVariable String email) {
        return patientRepository.findPatientByEmail(email);
    }

    @PostMapping
    public void addNewPatient(@PathVariable Patient patient) {
        patientRepository.addPatient(patient);
    }

    @DeleteMapping("/{email}")
    public void removePatient(@PathVariable String email) {
        patientRepository.removePatientByEmail(email);
    }

    @PutMapping("/{email}")
    public void editPatientByMail(@PathVariable String email, @RequestBody Patient patient) {
        patientService.editPatientByEmail(email, patient);
    }
}
