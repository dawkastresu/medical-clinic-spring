package com.dawkastresu.medicalclinic.model;

import com.dawkastresu.medicalclinic.command.RegisterPatientCommand;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name="PATIENTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String idCardNo;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    @Temporal(TemporalType.DATE)
    private LocalDate birthday;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void update(Patient patient) {
        patient.setEmail(patient.getEmail());
        patient.setFirstName(patient.getFirstName());
        patient.setLastName(patient.getLastName());
        patient.setPhoneNumber(patient.getPhoneNumber());
        patient.setBirthday(patient.getBirthday());
    }

    public static Patient create(RegisterPatientCommand command) {
        User user = new User();
            user.setUsername(command.getUsername());
            user.setPassword(command.getPassword());
        Patient patient = new Patient();
            patient.setFirstName(command.getFirstName());
            patient.setLastName(command.getLastName());
            patient.setEmail(command.getEmail());
            patient.setPhoneNumber(command.getPhoneNumber());
            patient.setIdCardNo(command.getIdCardNo());
            patient.setBirthday(command.getBirthday());
            patient.setUser(user);
        return patient;
    }

}
