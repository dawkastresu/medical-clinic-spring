package com.dawkastresu.medicalclinic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InstitutionRepository {
    private final List<Institution> institutions;

    public List<Institution> findAll() {
        return new ArrayList<>(institutions);
    }

    public void remove(String name) {
        institutions.removeIf(institution -> name.equalsIgnoreCase(institution.getName()));
    }

    public void add(Institution institution) {
        institutions.add(institution);
    }

    public Optional<Institution> findByName(String name) {
        return institutions.stream()
                .filter(institution -> name.equalsIgnoreCase(institution.getName()))
                .findFirst();
    }
}
