package com.dawkastresu.medicalclinic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAll();

    Optional<Appointment> findById(Long id);

    void deleteById(Long id);

}
