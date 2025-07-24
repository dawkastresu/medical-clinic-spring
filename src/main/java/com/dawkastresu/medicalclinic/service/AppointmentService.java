package com.dawkastresu.medicalclinic.service;

import com.dawkastresu.medicalclinic.command.CreateAppointmentCommand;
import com.dawkastresu.medicalclinic.dto.AppointmentDto;
import com.dawkastresu.medicalclinic.exception.AppointmentNotFoundException;
import com.dawkastresu.medicalclinic.exception.DoctorNotFoundException;
import com.dawkastresu.medicalclinic.exception.InvalidAppointmentDataException;
import com.dawkastresu.medicalclinic.exception.PatientNotFoundException;
import com.dawkastresu.medicalclinic.model.Appointment;
import com.dawkastresu.medicalclinic.model.Doctor;
import com.dawkastresu.medicalclinic.model.Patient;
import com.dawkastresu.medicalclinic.model.Specialization;
import com.dawkastresu.medicalclinic.repository.AppointmentRepository;
import com.dawkastresu.medicalclinic.repository.DoctorRepository;
import com.dawkastresu.medicalclinic.repository.PatientRepository;
import com.dawkastresu.medicalclinic.utils.AppointmentMapper;
import com.dawkastresu.medicalclinic.utils.AppointmentValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository repository;
    private final AppointmentMapper mapper;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public Page<AppointmentDto> getAll(Pageable pageable) {
        Page<Appointment> page = repository.findAll(pageable);
        return page.map(mapper::toDto);
//        List<AppointmentDto> pageContent = page.stream()
//                .map(mapper::toDto)
//                .toList();
//        return new PageImpl<>(pageContent, page.getPageable(), page.getTotalElements());
    }

    public Page<AppointmentDto> getDoctorsAppointments(Long id, Pageable pageable) {
        return repository.findByDoctor_Id(id, pageable)
                .map(mapper::toDto);
    }

    public Page<AppointmentDto> getAppointmentsBySpecializationAndDateRange(
            Specialization specialization,
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable
    ) {
        return repository
                .findByDoctor_SpecializationAndStartTimeBetweenAndPatientIdIsNull(
                        specialization,
                        from,
                        to,
                        pageable
                )
                .map(mapper::toDto);
    }

    public Page<AppointmentDto> getAvailableAppointments(
            LocalDate date,
            Specialization specialization,
            Pageable pageable
    ) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return repository
                .findByDoctor_SpecializationAndStartTimeBetween(
                        specialization,
                        startOfDay,
                        endOfDay,
                        pageable
                )
                .map(mapper::toDto);
    }

    public Page<AppointmentDto> getAvailableAppointmentsByTimeRangeAndSpecialization (
            LocalDate date,
            Specialization specialization,
            Pageable pageable
    ) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return repository
                .findByDoctor_SpecializationAndStartTimeBetweenAndPatientIdIsNull(
                        specialization,
                        startOfDay,
                        endOfDay,
                        pageable
                )
                .map(mapper::toDto);
    }

    public Page<AppointmentDto> getAvailableAppointmentsByTimeRange (
            LocalDate date,
            Pageable pageable
    ) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return repository
                .findByStartTimeBetweenAndPatientIdIsNull(
                        startOfDay,
                        endOfDay,
                        pageable
                )
                .map(mapper::toDto);
    }

    @Transactional
    public AppointmentDto addNew(CreateAppointmentCommand command) {
        Appointment appointment = Appointment.create(command);
        Doctor doctor = doctorRepository.findById(command.getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException("doctor with this id does not exist", HttpStatus.NOT_FOUND));
        appointment.setDoctor(doctor);
        AppointmentValidator.validateAppointment(repository, appointment, mapper);
        repository.save(appointment);
        return mapper.toDto(appointment);
    }

    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);
    }

    public AppointmentDto findById(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("there is no appointment with this id", HttpStatus.NOT_FOUND));
        return mapper.toDto(appointment);
    }

    @Transactional
    public AppointmentDto assignPatient(Long idPatient, Long appointmentId) {
        Appointment appointment = repository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("There is no appointment with this ID", HttpStatus.NOT_FOUND));

        Patient patient = patientRepository.findById(idPatient)
                .orElseThrow(() -> new PatientNotFoundException("There is no patient with this ID", HttpStatus.NOT_FOUND));

        List<Appointment> patientOverlaps = repository.findOverlappingAppointmentsForPatient(
                patient.getId(),
                appointment.getStartTime(),
                appointment.getEndTime()
        );

        if (!patientOverlaps.isEmpty()) {
            throw new InvalidAppointmentDataException("Patient already has an appointment at this time", HttpStatus.BAD_REQUEST);
        }

        appointment.setPatientId(patient.getId());
        repository.save(appointment);

        return mapper.toDto(appointment);
    }

    public Page<AppointmentDto> findByPatientId(Long id, Pageable pageable) {
        return repository.findByPatientId(id, pageable).map(mapper::toDto);
    }

}
