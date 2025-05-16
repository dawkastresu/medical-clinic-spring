package com.dawkastresu.medicalclinic;

import com.dawkastresu.medicalclinic.command.RegisterDoctorCommand;
import com.dawkastresu.medicalclinic.dto.DoctorDto;
import com.dawkastresu.medicalclinic.model.Doctor;
import com.dawkastresu.medicalclinic.model.Institution;
import com.dawkastresu.medicalclinic.model.Specialization;
import com.dawkastresu.medicalclinic.repository.DoctorRepository;
import com.dawkastresu.medicalclinic.repository.InstitutionRepository;
import com.dawkastresu.medicalclinic.service.DoctorService;
import com.dawkastresu.medicalclinic.utils.DoctorMapper;
import com.dawkastresu.medicalclinic.utils.DoctorValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DoctorServiceTest {

    DoctorRepository doctorRepository;
    InstitutionRepository institutionRepository;
    DoctorMapper doctorMapper;
    DoctorService doctorService;
    DoctorValidator doctorValidator;


    @BeforeEach
    void setup() {
        this.doctorMapper = Mappers.getMapper(DoctorMapper.class);
        this.doctorRepository = Mockito.mock(DoctorRepository.class);
        this.institutionRepository = Mockito.mock(InstitutionRepository.class);
        this.doctorValidator = Mockito.mock(DoctorValidator.class);
        this.doctorService = new DoctorService(doctorRepository, institutionRepository, doctorMapper);
        assertNotNull(this.institutionRepository, "institutionRepository should not be null");
        assertNotNull(this.doctorService, "doctorService should not be null");
    }

    @Test
    void getAll_doctorsExist_doctorsReturned() {
        //given
        List<Doctor> doctors = List.of(
                new Doctor(1L, "email", "password", "firstName", "lastName", new ArrayList<>(), Specialization.CARDIOLOGY, new ArrayList<>()),
                new Doctor(2L, "email2", "password2", "firstName2", "lastName2", new ArrayList<>(), Specialization.CARDIOLOGY, new ArrayList<>()),
                new Doctor(3L, "email3", "password3", "firstName3", "lastName3", new ArrayList<>(), Specialization.CARDIOLOGY, new ArrayList<>())
        );

        when(doctorRepository.findAll()).thenReturn(doctors);
        //when
        List<DoctorDto> result = doctorService.getAll();
        //then
        Assertions.assertAll(
                () -> assertEquals(1L, result.get(0).getId()),
                () -> assertEquals("email", result.get(0).getEmail()),
                () -> assertEquals("firstName", result.get(0).getFirstName()),
                () -> assertEquals("lastName", result.get(0).getLastName()),
                () -> assertEquals(new ArrayList<>(), result.get(0).getInstitutions()),
                () -> assertEquals(Specialization.CARDIOLOGY, result.get(0).getSpecialization()),
                () -> assertEquals(2L, result.get(1).getId()),
                () -> assertEquals(3L, result.get(2).getId())
        );
    }

    @Test
    void addNew_doctorCreated_doctorDtoReturned() {
        DoctorDto expectedDto = new DoctorDto(1L, "email@gmail.com", "firstName", "lastName", Specialization.CARDIOLOGY, new ArrayList<>());
        RegisterDoctorCommand command = new RegisterDoctorCommand("email@gmail.com", "password", "firstName", "lastName", Specialization.CARDIOLOGY, "name", "postalCode", "adress", new ArrayList<>());
        Doctor doctor = new Doctor(1L, "email@gmail.com", "password", "firstName", "lastName", new ArrayList<>(), Specialization.CARDIOLOGY, new ArrayList<>());

        when(doctorRepository.findAll()).thenReturn(new ArrayList<>()); // Dla findAll()

        try (MockedStatic<DoctorValidator> mockedStatic = Mockito.mockStatic(DoctorValidator.class)) {
            mockedStatic.when(() -> DoctorValidator.validateDoctor(doctorRepository, "firstName")).thenReturn(true);

            when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

            DoctorDto result = doctorService.addNew(command);

            Assertions.assertAll(
                    () -> assertEquals(expectedDto.getId(), result.getId()),
                    () -> assertEquals(expectedDto.getEmail(), result.getEmail()),
                    () -> assertEquals(expectedDto.getFirstName(), result.getFirstName()),
                    () -> assertEquals(expectedDto.getLastName(), result.getLastName()),
                    () -> assertEquals(expectedDto.getSpecialization(), result.getSpecialization())
            );
        }
    }

    @Test
    void findById_doctorExists_DoctorDtoReturned() throws Exception {
        Long id = 1L;
        Doctor doctor = new Doctor(1L, "email@gmail.com", "password", "firstName", "lastName", new ArrayList<>(), Specialization.CARDIOLOGY, new ArrayList<>());
        DoctorDto expectedDto = new DoctorDto(1L, "email@gmail.com", "firstName", "lastName", Specialization.CARDIOLOGY, new ArrayList<>());

        when(doctorRepository.findById(id)).thenReturn(Optional.of(doctor));
        DoctorDto result = doctorService.findById(id);

        Assertions.assertAll(
                () -> assertEquals(expectedDto.getId(), result.getId()),
                () -> assertEquals(expectedDto.getEmail(), result.getEmail()),
                () -> assertEquals(expectedDto.getFirstName(), result.getFirstName()),
                () -> assertEquals(expectedDto.getLastName(), result.getLastName()),
                () -> assertEquals(expectedDto.getSpecialization(), result.getSpecialization())
        );
    }

    @Test
    void editById_doctorExists_DoctorDtoReturned() throws Exception {
        Long id = 1L;
        Doctor doctor = new Doctor(1L, "email@gmail.com", "password", "firstName", "lastName", new ArrayList<>(), Specialization.CARDIOLOGY, new ArrayList<>());
        DoctorDto expectedDto = new DoctorDto(1L, "email@gmail.com", "firstName", "lastName", Specialization.CARDIOLOGY, new ArrayList<>());
        RegisterDoctorCommand command = new RegisterDoctorCommand("email@gmail.com", "password", "firstName", "lastName", Specialization.CARDIOLOGY, "name", "postalCode", "adress", List.of(1L));

        when(doctorRepository.findById(id)).thenReturn(Optional.of(doctor));
        when(institutionRepository.findAllById(command.getInstitutionIds())).thenReturn(new ArrayList<>()); // Konfiguracja institutionRepository
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor); // Konfiguracja save

        DoctorDto result = doctorService.editById(id, command);

        Assertions.assertAll(
                () -> assertEquals(expectedDto.getId(), result.getId()),
                () -> assertEquals(expectedDto.getEmail(), result.getEmail()),
                () -> assertEquals(expectedDto.getFirstName(), result.getFirstName()),
                () -> assertEquals(expectedDto.getLastName(), result.getLastName()),
                () -> assertEquals(expectedDto.getSpecialization(), result.getSpecialization())
        );
    }


}
