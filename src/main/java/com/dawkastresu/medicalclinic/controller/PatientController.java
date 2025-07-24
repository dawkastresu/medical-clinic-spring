package com.dawkastresu.medicalclinic.controller;

import com.dawkastresu.medicalclinic.command.CreatePatientCommand;
import com.dawkastresu.medicalclinic.command.RegisterPatientCommand;
import com.dawkastresu.medicalclinic.dto.PatientDto;
import com.dawkastresu.medicalclinic.model.Doctor;
import com.dawkastresu.medicalclinic.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//adnotacja RestController mówi, że ta klasa jest beanem obsługującym endpointy
@RestController
//RequiredArgsConstruktor generuje konstruktor który przyjmuje pola finalne
@RequiredArgsConstructor
//RequestMapping określa miejsce (endpoint), każdy endpoint w tym kontrolerze zaczynał się będzie od /patients
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @Operation(summary = "Get all patients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patients found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doctor.class)) }) })
    @GetMapping
    public Page<PatientDto> getAllPatients(Pageable pageable) {
        return patientService.getAll(pageable);
    }

    @Operation(summary = "Get patient by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doctor.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid mail supplied",
                    content =  @Content),
            @ApiResponse(responseCode = "404", description = "Patient not found",
                    content =  @Content) })
    @GetMapping("/{email}")
    public PatientDto getPatientByMail(@PathVariable String email) {
        return patientService.findPatientByEmail(email);
    }

    @Operation(summary = "Register patient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient registered",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doctor.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied",
                    content =  @Content) })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PatientDto registerPatient(@RequestBody RegisterPatientCommand command) {
        return patientService.addNew(command);
    }

    @Operation(summary = "Remove patient by mail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient removed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doctor.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid mail supplied",
                    content =  @Content),
            @ApiResponse(responseCode = "404", description = "Patient not found",
                    content =  @Content) })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{email}")
    public void removePatient(@PathVariable String email) {
        patientService.removeByMail(email);
    }

    @Operation(summary = "Edit patient by mail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doctor.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied",
                    content =  @Content),
            @ApiResponse(responseCode = "404", description = "Patient not found",
                    content =  @Content) })
    @PutMapping("/{email}")
    public PatientDto editPatientByMail(@PathVariable String email, @RequestBody CreatePatientCommand createPatientCommand) {
        return patientService.editByEmail(email, createPatientCommand);
    }

}
