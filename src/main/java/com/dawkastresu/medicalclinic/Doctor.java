package com.dawkastresu.medicalclinic;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="DOCTORS")
@Data
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private Specialization specialization;

    public void update(Doctor doctor) {
        doctor.setEmail(doctor.getEmail());
        doctor.setFirstName(doctor.getFirstName());
        doctor.setLastName(doctor.getLastName());
        doctor.setSpecialization(doctor.getSpecialization());
    }

}
