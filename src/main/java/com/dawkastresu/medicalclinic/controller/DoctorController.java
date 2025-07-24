package com.dawkastresu.medicalclinic.controller;

import com.dawkastresu.medicalclinic.command.AssignDoctorToInstitutionCommand;
import com.dawkastresu.medicalclinic.command.RegisterDoctorCommand;
import com.dawkastresu.medicalclinic.dto.DoctorDto;
import com.dawkastresu.medicalclinic.model.Doctor;
import com.dawkastresu.medicalclinic.model.Specialization;
import com.dawkastresu.medicalclinic.service.DoctorService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService service;

    @Operation(summary = "Get all doctors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctors found",
                content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Doctor.class)) }) })
    @GetMapping
    public Page<DoctorDto> getAllDoctors(Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping(params = {"specialization"})
    public Page<DoctorDto> getDoctorsBySpecialization(
            @RequestParam Specialization specialization,
            Pageable pageable
    ) {
        return service.getDoctorsBySpecialization(specialization, pageable);
    }

    @Operation(summary = "Get doctor by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doctor.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content =  @Content),
            @ApiResponse(responseCode = "404", description = "Doctor not found",
                    content =  @Content) })
    @GetMapping("/{id}")
    public DoctorDto getDoctorById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Add new doctor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doctor.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied",
                    content =  @Content) })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDto addNewDoctor(@RequestBody RegisterDoctorCommand command) {
        return service.addNew(command);
    }

    @Operation(summary = "Remove doctor by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor removed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doctor.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content =  @Content),
            @ApiResponse(responseCode = "404", description = "Doctor not found",
                    content =  @Content) })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDoctor(@PathVariable Long id) {
        service.remove(id);
    }

    @Operation(summary = "Edit doctor by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doctor.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied",
                    content =  @Content),
            @ApiResponse(responseCode = "404", description = "Doctor not found",
                    content =  @Content) })
    @PutMapping("/{id}")
    public DoctorDto editDoctorById(@PathVariable Long id, @RequestBody RegisterDoctorCommand command) {
        return service.editById(id, command);
    }

    @Operation(summary = "Assign doctor to institution")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor assigned",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doctor.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied",
                    content =  @Content) })
    @PatchMapping
    public void addToInstitution(@RequestBody AssignDoctorToInstitutionCommand command) {
        service.addDoctorToInstitution(command.getDoctorId(), command.getInstitutionId());
    }

}
