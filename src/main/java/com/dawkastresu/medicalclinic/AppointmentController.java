package com.dawkastresu.medicalclinic;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService service;

    @GetMapping
    public List<AppointmentDto> getAll() {
        return service.getAll();
    }

    @PostMapping
    public AppointmentDto addNew(@RequestBody CreateAppointmentCommand command) {
        return service.addNew(command);
    }

    @PatchMapping("/{appointmentId}")
    public AppointmentDto assignPatient(@PathVariable Long appointmentId, @RequestBody AssignPatientCommand command) {
        service.addPatient(command.getId(), appointmentId);
        return service.findById(appointmentId);
    }

    @GetMapping("/{patientId}")
    public List<AppointmentDto> getAppointmentsForPatient(@PathVariable Long patientId) {
        return service.getAll().stream()
                .filter(appointment -> appointment.getPatientId().equals(patientId))
                .toList();
    }

}
