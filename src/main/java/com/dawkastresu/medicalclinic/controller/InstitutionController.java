package com.dawkastresu.medicalclinic.controller;

import com.dawkastresu.medicalclinic.command.CreateInstitutionCommand;
import com.dawkastresu.medicalclinic.dto.InstitutionDto;
import com.dawkastresu.medicalclinic.model.Doctor;
import com.dawkastresu.medicalclinic.service.InstitutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/institutions")
public class InstitutionController {

    private final InstitutionService service;

    @Operation(summary = "Get all institutions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctor found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doctor.class)) }) })
    @GetMapping
    public Page<InstitutionDto> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Operation(summary = "Create new institution")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Institution created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doctor.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied",
                    content =  @Content) })
    @PostMapping
    public InstitutionDto addNew(@RequestBody CreateInstitutionCommand createInstitutionCommand) {
        return service.addNew(createInstitutionCommand);
    }

    @Operation(summary = "Remove institution by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Institution removed",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doctor.class)) }) })
    @DeleteMapping("/{name}")
    @Transactional //naruszenie wiezow integralnosci
    public void remove(@PathVariable String name) {
        service.remove(name);
    }

}
