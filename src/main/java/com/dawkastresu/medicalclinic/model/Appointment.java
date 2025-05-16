package com.dawkastresu.medicalclinic.model;

import com.dawkastresu.medicalclinic.command.CreateAppointmentCommand;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="APPOINTMENTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;

    private Long patientId;

    public static Appointment create(CreateAppointmentCommand command) {
        Appointment appointment = new Appointment();
        appointment.setStartTime(command.getStartTime());
        appointment.setEndTime(command.getEndTime());
        appointment.setPatientId(command.getPatientId());
        return appointment;
    }

}
