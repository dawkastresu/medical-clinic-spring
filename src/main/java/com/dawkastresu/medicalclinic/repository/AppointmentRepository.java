package com.dawkastresu.medicalclinic.repository;

import com.dawkastresu.medicalclinic.dto.AppointmentDto;
import com.dawkastresu.medicalclinic.model.Appointment;
import com.dawkastresu.medicalclinic.model.Doctor;
import com.dawkastresu.medicalclinic.model.Specialization;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Page<Appointment> findAll(Pageable pageable);

    Optional<Appointment> findById(Long id);

    void deleteById(Long id);

    @Query("""
    SELECT a FROM Appointment a
    WHERE a.doctor.id = :doctorId
    AND a.startTime < :endTime
    AND a.endTime > :startTime
    """)
    List<Appointment> findOverlappingAppointments(@Param("doctorId") Long doctorId,
                                                  @Param("startTime") LocalDateTime startTime,
                                                  @Param("endTime") LocalDateTime endTime);

    @Query("""
    SELECT a FROM Appointment a
    WHERE a.patientId = :patientId
      AND a.startTime < :endTime
      AND a.endTime > :startTime
    """)
    List<Appointment> findOverlappingAppointmentsForPatient(@Param("patientId") Long patientId,
                                                            @Param("startTime") LocalDateTime startTime,
                                                            @Param("endTime") LocalDateTime endTime);

    Page<Appointment> findByDoctor_Id(Long id, Pageable pageable);

    Page<Appointment> findByDoctor_SpecializationAndStartTimeBetweenAndPatientIdIsNull(
            Specialization specialization,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable);

    Page<Appointment> findByDoctor_SpecializationAndStartTimeBetween(
            Specialization specialization,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    );

    Page<Appointment> findByStartTimeBetweenAndPatientIdIsNull(
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    );

    Page<Appointment> findByPatientId(Long id, Pageable pageable);

    // 1. Liczba wszystkich wizyt wystawionych przez doktora
    long countByDoctorId(Long doctorId);

    // 2. Liczba wizyt doktora bez przypisanego pacjenta (niezapisane)
    long countByDoctorIdAndPatientIdIsNull(Long doctorId);

    // 3. Liczba wizyt doktora z przypisanym pacjentem (odbyłe się)
    long countByDoctorIdAndPatientIdIsNotNull(Long doctorId);

}
