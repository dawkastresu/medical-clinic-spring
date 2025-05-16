package com.dawkastresu.medicalclinic.repository;

import com.dawkastresu.medicalclinic.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findAll();

    Optional<Doctor> findByEmail(String email);

}
