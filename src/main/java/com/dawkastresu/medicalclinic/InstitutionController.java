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
    public List<Institution> getAll(){
        return service.getAll();
    }

    @PostMapping
    public Institution addNew(@RequestBody Institution institution) {
        service.addNew(institution);
        return institution;
    }

    @DeleteMapping("/{name}")
    public void remove(@PathVariable String name) {
        service.remove(name);
    }
}
