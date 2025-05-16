package com.dawkastresu.medicalclinic.repository;

import com.dawkastresu.medicalclinic.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    List<Institution> findAll();

    Optional<Institution> findByName(String name);

    void deleteByName(String name);

}
