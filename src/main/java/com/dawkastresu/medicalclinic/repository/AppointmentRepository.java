package com.dawkastresu.medicalclinic.repository;

import com.dawkastresu.medicalclinic.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAll();

    Optional<Appointment> findById(Long id);

    void deleteById(Long id);

}
