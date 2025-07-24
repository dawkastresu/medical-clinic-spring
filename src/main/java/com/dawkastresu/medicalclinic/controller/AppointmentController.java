package com.dawkastresu.medicalclinic.controller;

import com.dawkastresu.medicalclinic.command.AssignPatientCommand;
import com.dawkastresu.medicalclinic.command.CreateAppointmentCommand;
import com.dawkastresu.medicalclinic.dto.AppointmentDto;
import com.dawkastresu.medicalclinic.model.Doctor;
import com.dawkastresu.medicalclinic.model.Specialization;
import com.dawkastresu.medicalclinic.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService service;

    @Operation(summary = "Get all appointments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doctor.class)) })})
    @GetMapping
    public Page<AppointmentDto> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping(params = {"specialization", "from", "to"})
    public Page<AppointmentDto> getAppointmentsBySpecializationAndDateRange(
            @RequestParam Specialization specialization,
            @RequestParam("from") LocalDateTime from,
            @RequestParam("to") LocalDateTime to,
            Pageable pageable
    ) {
        return service.getAppointmentsBySpecializationAndDateRange(specialization, from, to, pageable);
    }

    @GetMapping("/available")
    public Page<AppointmentDto> getAvailableAppointmentsForTimeRange(
            @RequestParam LocalDate date,
            Pageable pageable
    ) {
        return service.getAvailableAppointmentsByTimeRange(date, pageable);
    }

    @GetMapping("/doctor/{id}")
    public Page<AppointmentDto> getDoctorsAppointments(@PathVariable("id") Long id, Pageable pageable) {
        return service.getDoctorsAppointments(id, pageable);
    }

    @Operation(summary = "Create new appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doctor.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied",
                    content =  @Content) })
    @PostMapping
    public AppointmentDto addNew(@RequestBody CreateAppointmentCommand command) {
        return service.addNew(command);
    }

    @Operation(summary = "Assign patient to appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient assigned",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doctor.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied",
                    content =  @Content) })
    @PatchMapping("/{appointmentId}")
    public AppointmentDto assignPatient(@PathVariable Long appointmentId, @RequestBody AssignPatientCommand command) {
        service.assignPatient(command.getId(), appointmentId);
        return service.findById(appointmentId);
    }

    @Operation(summary = "Get appointments for patient by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doctor.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content =  @Content),
            @ApiResponse(responseCode = "404", description = "patient not found",
                    content =  @Content) })
    @GetMapping("/patient/{id}")
    public Page<AppointmentDto> getAppointmentsForPatient(@PathVariable Long id, Pageable pageable) {
        return service.findByPatientId(id, pageable);
    }

}
