//package com.dawkastresu.medicalclinic;
//
//import com.dawkastresu.medicalclinic.command.CreateAppointmentCommand;
//import com.dawkastresu.medicalclinic.dto.AppointmentDto;
//import com.dawkastresu.medicalclinic.dto.SimpleDoctorDto;
//import com.dawkastresu.medicalclinic.exception.AppointmentNotFoundException;
//import com.dawkastresu.medicalclinic.exception.DoctorNotFoundException;
//import com.dawkastresu.medicalclinic.exception.InvalidAppointmentDataException;
//import com.dawkastresu.medicalclinic.exception.PatientNotFoundException;
//import com.dawkastresu.medicalclinic.model.*;
//import com.dawkastresu.medicalclinic.repository.AppointmentRepository;
//import com.dawkastresu.medicalclinic.repository.DoctorRepository;
//import com.dawkastresu.medicalclinic.repository.PatientRepository;
//import com.dawkastresu.medicalclinic.service.AppointmentService;
//import com.dawkastresu.medicalclinic.utils.AppointmentMapper;
//import com.dawkastresu.medicalclinic.utils.AppointmentValidator;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//public class AppointmentServiceTest {
//
//    AppointmentRepository appointmentRepository;
//    AppointmentService appointmentService;
//    AppointmentMapper appointmentMapper;
//    AppointmentValidator validator;
//    PatientRepository patientRepository;
//    DoctorRepository doctorRepository;
//
//    @BeforeEach
//    void setup() {
//        this.appointmentMapper = Mockito.mock(AppointmentMapper.class);
//        this.appointmentRepository = Mockito.mock(AppointmentRepository.class);
//        this.doctorRepository = Mockito.mock(DoctorRepository.class);
//        this.patientRepository = Mockito.mock(PatientRepository.class);
//        this.appointmentService = new AppointmentService(appointmentRepository, appointmentMapper, patientRepository, doctorRepository);
//    }
//
//    @Test
//    void getAll_appointmentsExist_appointmentDtoListReturned() throws Exception {
//        LocalDateTime startTime = LocalDateTime.now();
//        LocalDateTime endTime = LocalDateTime.now().plusMinutes(45);
//
//        Doctor doctor1 = new Doctor(1L, "email@gmail.com", "password", "firstName", "lastName", new ArrayList<>(), Specialization.CARDIOLOGY, new ArrayList<>());
//        Doctor doctor2 = new Doctor(2L, "email2@gmail.com", "password2", "firstName2", "lastName2", new ArrayList<>(), Specialization.CARDIOLOGY, new ArrayList<>());
//
//        Appointment appointment1 = new Appointment(1L, startTime, endTime, doctor1, 1L);
//        Appointment appointment2 = new Appointment(2L, startTime, endTime, doctor2, 2L);
//
//        List<Appointment> appointments = List.of(appointment1, appointment2);
//
//        when(appointmentRepository.findAll()).thenReturn(appointments);
//
//        when(appointmentMapper.toDto(appointment1)).thenReturn(new AppointmentDto(1L, startTime, endTime, 1L,
//                new SimpleDoctorDto(1L, "email@gmail.com", "firstName", "lastName", Specialization.CARDIOLOGY)));
//
//        when(appointmentMapper.toDto(appointment2)).thenReturn(new AppointmentDto(2L, startTime, endTime, 2L,
//                new SimpleDoctorDto(2L, "email2@gmail.com", "firstName2", "lastName2", Specialization.CARDIOLOGY)));
//
//        Pageable page = PageRequest.of(0, 2);
//        Page<AppointmentDto> result = appointmentService.getAll(page);
//
////        Assertions.assertAll(
////                () -> assertEquals(1L, result.get(0).getId()),
////                () -> assertEquals(2L, result.get(1).getId())
////        );
//    }
//
//
//    @Test
//    void addNew_appointmentCreated_appointmentDtoReturned() {
//        // given
//        LocalDateTime startTime = LocalDateTime.of(2025, 7, 20, 12, 15);
//        LocalDateTime endTime = LocalDateTime.of(2025, 7, 20, 13, 0);
//        Long doctorId = 1L;
//        Long patientId = 1L;
//
//        CreateAppointmentCommand command = new CreateAppointmentCommand(startTime, endTime, doctorId, patientId);
//        Doctor doctor = new Doctor(doctorId, "email@gmail.com", "password", "firstName", "lastName",
//                new ArrayList<>(), Specialization.CARDIOLOGY, new ArrayList<>());
//
//        Appointment savedAppointment = new Appointment(1L, startTime, endTime, doctor, patientId);
//
//        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
//        when(appointmentRepository.findAll()).thenReturn(new ArrayList<>());
//        when(appointmentRepository.save(any(Appointment.class))).thenReturn(savedAppointment);
//
//        AppointmentDto expectedDto = new AppointmentDto(1L, startTime, endTime, patientId,
//                new SimpleDoctorDto(doctorId, "email@gmail.com", "firstName", "lastName", Specialization.CARDIOLOGY));
//
//        // naprawa tutaj
//        when(appointmentMapper.toDto(any(Appointment.class))).thenReturn(expectedDto);
//
//        // when
//        AppointmentDto result = appointmentService.addNew(command);
//
//        // then
//        assertNotNull(result, "Returned DTO should not be null");
//        assertEquals(1L, result.getId(), "Appointment ID should be 1");
//    }
//
//
//    @Test
//    void assignPatient_patientAssignedToAppointment_appointmentDtoReturned() {
//        // given
//        Long appointmentId = 1L;
//        Long patientId = 2L;
//        LocalDateTime startTime = LocalDateTime.of(2025, 7, 20, 12, 15);
//        LocalDateTime endTime = LocalDateTime.of(2025, 7, 20, 13, 0);
//        Long doctorId = 1L;
//
//        Doctor doctor = new Doctor(doctorId, "email@gmail.com", "password", "firstName", "lastName",
//                new ArrayList<>(), Specialization.CARDIOLOGY, new ArrayList<>());
//        Appointment appointment = new Appointment(appointmentId, startTime, endTime, doctor, null); // Brak pacjenta początkowo
//        Patient patient = new Patient(patientId, "email@gmail.com", "idCardNo", "firstName", "lastName", "phoneNumber", LocalDate.now().minusYears(22L), new User());
//        Appointment updatedAppointment = new Appointment(appointmentId, startTime, endTime, doctor, patientId); // Wizyta z pacjentem
//
//        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));
//        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
//        when(appointmentRepository.save(any(Appointment.class))).thenReturn(updatedAppointment);
//
//        AppointmentDto expectedDto = new AppointmentDto(appointmentId, startTime, endTime, patientId,
//                new SimpleDoctorDto(doctorId, "email@gmail.com", "firstName", "lastName", Specialization.CARDIOLOGY));
//
//        when(appointmentMapper.toDto(appointment)).thenReturn(expectedDto);
//
//        // when
//        AppointmentDto result = appointmentService.assignPatient(patientId, appointmentId);
//
//        // then
//        assertEquals(appointmentId, result.getId());
//        assertEquals(patientId, result.getPatientId());
//    }
//
//    @Test
//    void removeAppointment_appointmentRemoved() throws Exception {
//        long id = 1;
//        appointmentService.remove(id);
//
//        verify(appointmentRepository).deleteById(id);
//    }
//
//    @Test
//    void getAll_appointmentNotFound() {
//        when(appointmentRepository.findAll()).thenReturn(Collections.emptyList());
//        Pageable page = PageRequest.of(0, 2);
//
//        AppointmentNotFoundException exception = assertThrows(AppointmentNotFoundException.class, () -> appointmentService.getAll(page));
//        assertEquals("Appointment not found", exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
//    }
//
//    @Test
//    void addNew_doctorNotFound() {
//        LocalDateTime startTime = LocalDateTime.of(2025, 7, 20, 12, 15);
//        LocalDateTime endTime = LocalDateTime.of(2025, 7, 20, 13, 0);
//        Long doctorId = 1L;
//        Long patientId = 1L;
//        CreateAppointmentCommand command = new CreateAppointmentCommand(startTime, endTime, doctorId, patientId);
//        long id = 1;
//        when(appointmentRepository.findById(any())).thenReturn(Optional.empty());
//
//        DoctorNotFoundException exception = assertThrows(DoctorNotFoundException.class, () -> appointmentService.addNew(command));
//        assertEquals("doctor with this id does not exist", exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
//    }
//
//    @Test
//    void assignPatient_appointmentNotFound() {
//        when(appointmentRepository.findById(any())).thenReturn(Optional.empty());
//
//        AppointmentNotFoundException exception = assertThrows(AppointmentNotFoundException.class, () -> appointmentService.assignPatient(1L, 1L));
//        assertEquals("there is no appointment with this id", exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
//    }
//
//    @Test
//    void assignPatient_patientNotFound() {
//        Appointment mockAppointment = new Appointment();
//        when(appointmentRepository.findById(any())).thenReturn(Optional.of(mockAppointment));
//        when(patientRepository.findById(any())).thenReturn(Optional.empty());
//
//        PatientNotFoundException exception = assertThrows(PatientNotFoundException.class, () -> appointmentService.assignPatient(1L, 1L));
//        assertEquals("There is no patient with this id", exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
//    }
//
//    @Test
//    void assignPatient_InvalidAppointmentData() {
//        LocalDateTime startTime = LocalDateTime.of(2025, 7, 20, 12, 15);
//        LocalDateTime endTime = LocalDateTime.of(2025, 7, 20, 13, 0);
//        List<Appointment> overlappingAppointments = List.of(
//                new Appointment(1L, startTime, endTime, new Doctor(), 1L)
//        );
//        when(appointmentRepository.findOverlappingAppointmentsForPatient(any(), any(), any())).thenReturn(overlappingAppointments);
//
//        InvalidAppointmentDataException exception = assertThrows(InvalidAppointmentDataException.class, () -> appointmentService.assignPatient(any(), any()));
//        assertEquals("Patient already has an appointment at this time", exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
//    }
//
////    @Test
////    void findPatientById_patientNotFound() {
////        when(appointmentRepository.findById(any())).thenReturn(Optional.empty());
////
////        PatientNotFoundException exception = assertThrows(PatientNotFoundException.class, () -> appointmentService.findByPatientId(any()));
////        assertEquals("There is no patient with this id", exception.getMessage());
////        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
////    }
//
//
//}
