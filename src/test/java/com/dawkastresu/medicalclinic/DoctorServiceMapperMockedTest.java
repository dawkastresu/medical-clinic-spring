package com.dawkastresu.medicalclinic;

import com.dawkastresu.medicalclinic.command.CreateDoctorCommand;
import com.dawkastresu.medicalclinic.command.RegisterDoctorCommand;
import com.dawkastresu.medicalclinic.dto.DoctorDto;
import com.dawkastresu.medicalclinic.exception.DoctorNotFoundException;
import com.dawkastresu.medicalclinic.model.Doctor;
import com.dawkastresu.medicalclinic.model.Institution;
import com.dawkastresu.medicalclinic.model.Password;
import com.dawkastresu.medicalclinic.model.Specialization;
import com.dawkastresu.medicalclinic.repository.DoctorRepository;
import com.dawkastresu.medicalclinic.repository.InstitutionRepository;
import com.dawkastresu.medicalclinic.service.DoctorService;
import com.dawkastresu.medicalclinic.utils.DoctorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DoctorServiceMapperMockedTest {

    private DoctorRepository doctorRepository;
    private InstitutionRepository institutionRepository;
    private DoctorMapper doctorMapper;
    private DoctorService doctorService;

    @BeforeEach
    void setUp() {
        doctorRepository = Mockito.mock(DoctorRepository.class);
        institutionRepository = Mockito.mock(InstitutionRepository.class);
        doctorMapper = Mockito.mock(DoctorMapper.class);
        doctorService = new DoctorService(doctorRepository, institutionRepository, doctorMapper);
    }

//    @Test
//    void getAll_mapperIsUsed() {
//        List<Doctor> doctors = List.of(
//                new Doctor(1L, "email", "password", "first", "last", new ArrayList<>(), Specialization.CARDIOLOGY, new ArrayList<>())
//        );
//
//        DoctorDto doctorDto = new DoctorDto(1L, "email", "first", "last", Specialization.CARDIOLOGY, new ArrayList<>());
//
//        when(doctorRepository.findAll()).thenReturn(doctors);
//        when(doctorMapper.toDto(any(Doctor.class))).thenReturn(doctorDto);
//
//        List<DoctorDto> result = doctorService.getAll();
//
//        assertEquals(1, result.size());
//        assertEquals("email", result.get(0).getEmail());
//        verify(doctorMapper).toDto(doctors.get(0));
//    }

    @Test
    void addNew_withRegisterDoctorCommand_returnsExpectedDto() {
        RegisterDoctorCommand registerCommand = new RegisterDoctorCommand(
                "email@gmail.com", "password", "John", "Doe", Specialization.CARDIOLOGY
        );

        Doctor doctor = new Doctor(
                null,
                "email@gmail.com",
                "password123",
                "John",
                "Doe",
                new ArrayList<>(),
                Specialization.CARDIOLOGY,
                new ArrayList<>()
        );

        Doctor savedDoctor = new Doctor(
                1L,
                "email@gmail.com",
                "password123",
                "John",
                "Doe",
                new ArrayList<>(),
                Specialization.CARDIOLOGY,
                new ArrayList<>()
        );

        DoctorDto expectedDto = new DoctorDto(
                1L,
                "email@gmail.com",
                "John",
                "Doe",
                Specialization.CARDIOLOGY,
                new ArrayList<>()
        );

        when(doctorRepository.findAll()).thenReturn(List.of()); // dla validatora
        when(doctorRepository.save(any(Doctor.class))).thenReturn(savedDoctor);
        when(doctorMapper.toDto(any(Doctor.class))).thenReturn(expectedDto);

        DoctorDto result = doctorService.addNew(registerCommand);

        assertEquals(expectedDto.getEmail(), result.getEmail());
        assertEquals(expectedDto.getFirstName(), result.getFirstName());
        assertEquals(expectedDto.getLastName(), result.getLastName());
    }



    @Test
    void findById_mapperIsUsed() throws Exception {
        Long id = 1L;
        Doctor doctor = new Doctor(id, "email", "pass", "first", "last", new ArrayList<>(), Specialization.CARDIOLOGY, new ArrayList<>());
        DoctorDto dto = new DoctorDto(id, "email", "first", "last", Specialization.CARDIOLOGY, new ArrayList<>());

        when(doctorRepository.findById(id)).thenReturn(Optional.of(doctor));
        when(doctorMapper.toDto(doctor)).thenReturn(dto);

        DoctorDto result = doctorService.findById(id);

        assertEquals("email", result.getEmail());
        verify(doctorMapper).toDto(doctor);
    }

    @Test
    void editById_mapperIsUsed() {
        Long id = 1L;
        RegisterDoctorCommand command = new RegisterDoctorCommand("email", "pass", "first", "last", Specialization.CARDIOLOGY);
        Doctor existing = new Doctor(id, "email", "pass", "first", "last", new ArrayList<>(), Specialization.CARDIOLOGY, new ArrayList<>());
        DoctorDto dto = new DoctorDto(id, "email", "first", "last", Specialization.CARDIOLOGY, new ArrayList<>());

        when(doctorRepository.findById(id)).thenReturn(Optional.of(existing));
        when(doctorRepository.save(any())).thenReturn(existing);
        when(doctorMapper.toDto(existing)).thenReturn(dto);

        DoctorDto result = doctorService.editById(id, command);

        assertEquals("email", result.getEmail());
        verify(doctorMapper).toDto(existing);
    }
}
