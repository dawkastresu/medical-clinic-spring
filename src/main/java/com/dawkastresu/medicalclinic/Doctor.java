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

    private String password;

    private String firstName;

    private String lastName;

    private Specialization specialization;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "institution_id", referencedColumnName = "id")
    private List<Institution> institutions;

    public void update(Doctor doctor) {
        doctor.setEmail(doctor.getEmail());
        doctor.setFirstName(doctor.getFirstName());
        doctor.setLastName(doctor.getLastName());
        doctor.setSpecialization(doctor.getSpecialization());
    }

    public static Doctor create(RegisterDoctorCommand command) {
        Institution institution = new Institution();
            institution.setName(command.getName());
            institution.setAdress(command.getAdress());
            institution.setPostalCode(command.getPostalCode());
        Doctor doctor = new Doctor();
            doctor.setFirstName(command.getFirstName());
            doctor.setLastName(command.getLastName());
            doctor.setEmail(command.getEmail());
            doctor.setSpecialization(command.getSpecialization());
            doctor.setInstitutions(List.of(institution));
    }

}
