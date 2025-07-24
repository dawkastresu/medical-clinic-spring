package com.dawkastresu.medicalclinic.repository;

import com.dawkastresu.medicalclinic.model.Doctor;
import com.dawkastresu.medicalclinic.model.Specialization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Page<Doctor> findAll(Pageable pageable);

    Optional<Doctor> findByEmail(String email);

    Page<Doctor> findDoctorBySpecialization(Specialization specialization, Pageable pageable);

}
