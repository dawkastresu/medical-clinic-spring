package com.dawkastresu.medicalclinic.repository;

import com.dawkastresu.medicalclinic.model.Institution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    Page<Institution> findAll(Pageable pageable);

    Optional<Institution> findByName(String name);

    void deleteByName(String name);

}
