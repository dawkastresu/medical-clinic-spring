package com.dawkastresu.medicalclinic;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="DOCTORS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointment;

    @Enumerated(value = EnumType.STRING)
    private Specialization specialization;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Doctor_Institution",
            joinColumns = { @JoinColumn(name = "institution_id") },
            inverseJoinColumns = { @JoinColumn(name = "doctor_id")})
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
            doctor.setPassword(doctor.getPassword());
            doctor.setLastName(command.getLastName());
            doctor.setEmail(command.getEmail());
            doctor.setSpecialization(command.getSpecialization());
            doctor.setInstitutions(List.of(institution));
            return doctor;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Doctor))
            return false;

        Doctor other = (Doctor) o;

        return id != null &&
                id.equals(other.getId());
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialization=" + specialization +
                '}';
    }

}
