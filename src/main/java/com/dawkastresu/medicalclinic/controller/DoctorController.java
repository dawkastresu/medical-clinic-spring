package com.dawkastresu.medicalclinic.controller;

import com.dawkastresu.medicalclinic.command.RegisterDoctorCommand;
import com.dawkastresu.medicalclinic.dto.DoctorDto;
import com.dawkastresu.medicalclinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService service;

    @GetMapping
    public List<DoctorDto> getAllDoctors() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public DoctorDto getDoctorById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDto addNewDoctor(@RequestBody RegisterDoctorCommand command) {
        return service.addNew(command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDoctor(@PathVariable Long id) {
        service.remove(id);
    }

    @PutMapping("/{id}")
    public DoctorDto editDoctorById(@PathVariable Long id, @RequestBody RegisterDoctorCommand command) {
        return service.editById(id, command);
    }

    @PatchMapping("/{id}")
    public void addToInstitution(@PathVariable Long id, @RequestBody Long idInstitution) {
        service.addDoctorToInstitution(id, idInstitution);
    }

}
