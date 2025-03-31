package com.dawkastresu.medicalclinic;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/institutions")
public class InstitutionController {

    private final InstitutionService service;

    @GetMapping
    public List<InstitutionDto> getAll(){
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
