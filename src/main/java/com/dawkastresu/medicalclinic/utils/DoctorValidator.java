package com.dawkastresu.medicalclinic.utils;

import com.dawkastresu.medicalclinic.exception.InvalidDoctorDataException;
import com.dawkastresu.medicalclinic.model.Doctor;
import com.dawkastresu.medicalclinic.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public final class DoctorValidator {

    public static boolean validateDoctor(DoctorRepository repository, String firstName){
        List<Doctor> doctors = repository.findAll();
        return doctors.stream()
                .noneMatch(doctor -> firstName.equalsIgnoreCase(doctor.getFirstName()));
    }

    public void validateEmail(String email, DoctorRepository repository) {
        Optional<Doctor> doctorByEmail = repository.findByEmail(email);
        if (doctorByEmail.isPresent()) {
            throw new InvalidDoctorDataException("Doctor with given mail exists.", HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateDoctorEdit(Doctor doctor) {
        if (doctor.getEmail() == null || doctor.getFirstName() == null) {
            throw new InvalidDoctorDataException("Doctor email or name is null", HttpStatus.BAD_REQUEST);
        }
    }

    public static void newValueNotNullValidate(Doctor doctor){
        if (doctor.getFirstName() == null || doctor.getLastName() == null || doctor.getEmail() == null || doctor.getSpecialization() == null) {
            throw new InvalidDoctorDataException("You cannot change any doctor data value to null.", HttpStatus.BAD_REQUEST);
        }
    }

}
