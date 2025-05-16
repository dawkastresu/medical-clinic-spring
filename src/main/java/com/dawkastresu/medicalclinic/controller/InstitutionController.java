package com.dawkastresu.medicalclinic.controller;

import com.dawkastresu.medicalclinic.command.CreateInstitutionCommand;
import com.dawkastresu.medicalclinic.dto.InstitutionDto;
import com.dawkastresu.medicalclinic.service.InstitutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/institutions")
public class InstitutionController {

    private final InstitutionService service;

    @GetMapping
    public List<InstitutionDto> getAll() {
        return service.getAll();
    }

    @PostMapping
    public InstitutionDto addNew(@RequestBody CreateInstitutionCommand createInstitutionCommand) {
        return service.addNew(createInstitutionCommand);
    }

    @DeleteMapping("/{name}")
    public void remove(@PathVariable String name) {
        service.remove(name);
    }

}
