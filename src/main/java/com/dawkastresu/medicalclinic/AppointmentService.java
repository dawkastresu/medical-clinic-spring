package com.dawkastresu.medicalclinic;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository repository;
    private final AppointmentMapper mapper;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public List<AppointmentDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public AppointmentDto addNew(CreateAppointmentCommand command) {
        Appointment appointment = Appointment.create(command);
        Doctor doctor = doctorRepository.findById(command.getDoctorId())
                .orElseThrow(() -> new DoctorNotFoundException("doctor with this id does not exist", HttpStatus.BAD_REQUEST));
        appointment.setDoctor(doctor);
        AppointmentValidator.validateAppointment(repository, appointment);
        repository.save(appointment);
        return mapper.toDto(appointment);
    }

    public void remove(Long id) {
        repository.deleteById(id);
    }

    public AppointmentDto findById(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("there is no appointment with this id", HttpStatus.NOT_FOUND));
        return mapper.toDto(appointment);
    }

    public AppointmentDto addPatient(Long idPatient, Long appointmentId) {
        Appointment appointment = repository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException("there is no appointment with this id", HttpStatus.NOT_FOUND));
        Patient patient = patientRepository.findById(idPatient)
                .orElseThrow(() -> new PatientNotFoundException("There is no patient with this id", HttpStatus.NOT_FOUND));
        appointment.setPatientId(patient.getId());
        repository.save(appointment);
        return mapper.toDto(appointment);
    }

}
