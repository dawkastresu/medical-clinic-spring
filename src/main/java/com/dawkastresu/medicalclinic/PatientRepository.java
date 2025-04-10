package com.dawkastresu.medicalclinic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findAll();

    Optional<Patient> findByEmail(String email);

    void deleteByEmail(String email);

}
