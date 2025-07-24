package com.dawkastresu.medicalclinic;

import com.dawkastresu.medicalclinic.command.CreatePatientCommand;
import com.dawkastresu.medicalclinic.command.RegisterPatientCommand;
import com.dawkastresu.medicalclinic.dto.PatientDto;
import com.dawkastresu.medicalclinic.dto.UserDto;
import com.dawkastresu.medicalclinic.exception.InstitutionNotFoundException;
import com.dawkastresu.medicalclinic.exception.PatientNotFoundException;
import com.dawkastresu.medicalclinic.model.Patient;
import com.dawkastresu.medicalclinic.model.User;
import com.dawkastresu.medicalclinic.repository.PatientRepository;
import com.dawkastresu.medicalclinic.service.PatientService;
import com.dawkastresu.medicalclinic.utils.PatientMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.when;

public class PatientServiceTest {

    PatientService patientService;
    PatientRepository patientRepository;
    PatientMapper patientMapper;

    @BeforeEach
    void setup() {
        this.patientMapper = Mappers.getMapper(PatientMapper.class);
        this.patientRepository = Mockito.mock(PatientRepository.class);
        this.patientService = new PatientService(patientRepository, patientMapper);
    }

    @Test
    void findPatientByEmail_patientExists_patientReturned() {
        // given
        String email = "example@gmail.com";
        LocalDate birthDate = LocalDate.of(1998, 4, 24);
        User user = new User(1L, "username", "password", null);
        Patient patient = new Patient(1L, "email", "idCardNo", "firstName", "lastName", "phoneNumber", birthDate, user);
        when(patientRepository.findByEmail(email)).thenReturn(Optional.of(patient));
        //when
        PatientDto result = patientService.findPatientByEmail(email);
        //then
        Assertions.assertAll(
                () -> assertEquals(1L, result.getId()),
                () -> assertEquals("email", result.getEmail()),
                () -> assertEquals("idCardNo", result.getIdCardNo()),
                () -> assertEquals("firstName", result.getFirstName()),
                () -> assertEquals("lastName", result.getLastName()),
                () -> assertEquals("phoneNumber", result.getPhoneNumber()),
                () -> assertEquals(birthDate, result.getBirthday()),
                () -> assertEquals(user, result.getUser())
        );
    }

    @Test
    void getAll_patientsExist_patientsPageReturned() {
        //given
        LocalDate birthDate = LocalDate.of(1998, 4, 24);
        List<Patient> patients = List.of(
                new Patient(1L, "email", "idCardNo", "firstName", "lastName", "phoneNumber", birthDate, null),
                new Patient(2L, "email1", "idCardNo1", "firstName1", "lastName1", "phoneNumber1", birthDate, null),
                new Patient(3L, "email2", "idCardNo2", "firstName2", "lastName2", "phoneNumber2", birthDate, null)
        );

        Pageable pageable = PageRequest.of(0, 10);
        Page<Patient> patientPage = new PageImpl<>(patients, pageable, patients.size());

        when(patientRepository.findAll(any(Pageable.class))).thenReturn(patientPage);

        //when
        Page<PatientDto> result = patientService.getAll(pageable);

        //then
        Assertions.assertAll(
                () -> assertEquals(3, result.getContent().size()),
                () -> assertEquals(1L, result.getContent().get(0).getId()),
                () -> assertEquals("email", result.getContent().get(0).getEmail()),
                () -> assertEquals("idCardNo", result.getContent().get(0).getIdCardNo()),
                () -> assertEquals("firstName", result.getContent().get(0).getFirstName()),
                () -> assertEquals("lastName", result.getContent().get(0).getLastName()),
                () -> assertEquals("phoneNumber", result.getContent().get(0).getPhoneNumber()),
                () -> assertEquals(birthDate, result.getContent().get(0).getBirthday()),
                () -> assertEquals(null, result.getContent().get(0).getUser()),
                () -> assertEquals(2L, result.getContent().get(1).getId()),
                () -> assertEquals("email1", result.getContent().get(1).getEmail()),
                () -> assertEquals("firstName1", result.getContent().get(1).getFirstName()),
                () -> assertEquals(3L, result.getContent().get(2).getId()),
                () -> assertEquals("email2", result.getContent().get(2).getEmail()),
                () -> assertEquals("firstName2", result.getContent().get(2).getFirstName()),
                () -> assertEquals(3, result.getTotalElements()),
                () -> assertEquals(1, result.getTotalPages()),
                () -> assertEquals(0, result.getNumber()),
                () -> assertEquals(10, result.getSize()),
                () -> assertTrue(result.isFirst()),
                () -> assertTrue(result.isLast()),
                () -> assertFalse(result.isEmpty())
        );
    }

    @Test
    void addNew_patientCreated_patientDtoReturned() {
        //given
        RegisterPatientCommand command = new RegisterPatientCommand(
                "email@example.com",
                "idCardNo",
                "John",
                "Doe",
                "123456789",
                LocalDate.of(1990, 1, 1),
                "username",
                "password",
                null
        );

        Patient patient = new Patient(
                1L,
                command.getEmail(),
                command.getIdCardNo(),
                command.getFirstName(),
                command.getLastName(),
                command.getPhoneNumber(),
                command.getBirthday(),
                command.getUser()
        );

        PatientDto expectedDto = new PatientDto(
                1L,
                "email@example.com",
                "John",
                "Doe",
                "123456789",
                LocalDate.of(1990, 1, 1),
                "John Doe",
                "idCardNo",
                new UserDto(1L, "username")
        );

        try (MockedStatic<Patient> mockedStatic = Mockito.mockStatic(Patient.class)) {
            mockedStatic.when(() -> Patient.create(command)).thenReturn(patient);
            when(patientRepository.save(patient)).thenReturn(patient);
            //when
            PatientDto result = patientService.addNew(command);
            // then
            Assertions.assertAll(
                    () -> assertEquals(expectedDto.getId(), result.getId()),
                    () -> assertEquals(expectedDto.getEmail(), result.getEmail()),
                    () -> assertEquals(expectedDto.getIdCardNo(), result.getIdCardNo()),
                    () -> assertEquals(expectedDto.getFirstName(), result.getFirstName()),
                    () -> assertEquals(expectedDto.getLastName(), result.getLastName()),
                    () -> assertEquals(expectedDto.getPhoneNumber(), result.getPhoneNumber()),
                    () -> assertEquals(expectedDto.getBirthday(), result.getBirthday())
            );
        }
    }

        @Test
        void editByEmail_patientExists_patientDtoReturned () {
            //given
            String email = "email@gmail.com";
            LocalDate birthDate = LocalDate.of(1998, 4, 24);
            CreatePatientCommand createPatientCommand = new CreatePatientCommand(
                    "email",
                    "password",
                    "idCardNo",
                    "firstName",
                    "lastName",
                    "phoneNumber",
                    birthDate
            );

            User user = new User(1L, "username", "password", null);
            Patient patient = new Patient(1L, "email", "idCardNo", "firstName", "lastName", "phoneNumber", birthDate, user);

            when(patientRepository.findByEmail(email)).thenReturn(Optional.of(patient));
            when(patientRepository.save(patient)).thenReturn(patient);
            //when
            PatientDto result = patientService.editByEmail(email, createPatientCommand);
            //then
            Assertions.assertAll(
                    () -> assertEquals("email", result.getEmail()),
                    () -> assertEquals("idCardNo", result.getIdCardNo()),
                    () -> assertEquals("firstName", result.getFirstName()),
                    () -> assertEquals("lastName", result.getLastName()),
                    () -> assertEquals("phoneNumber", result.getPhoneNumber()),
                    () -> assertEquals("email", result.getEmail()),
                    () -> assertEquals(birthDate, result.getBirthday())
            );
        }

//    @Test
//    void getAll_patientNotFound() {
//        when(patientRepository.findAll()).thenReturn(Collections.emptyList());
//
//        PatientNotFoundException exception = assertThrows(PatientNotFoundException.class, () -> patientService.getAll());
//        assertEquals("No patients found", exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
//    }

//    @Test
//    void editByEmail_patientNotFound() {
//        when(patientRepository.findByEmail(any())).thenReturn(Optional.empty());
//
//        PatientNotFoundException exception = assertThrows(PatientNotFoundException.class, () -> patientService.editByEmail(any(), any()));
//        assertEquals("Patient not found", exception.getMessage());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
//    }

    @Test
    void findPatientByEmail_patientNotFound() {
        when(patientRepository.findByEmail(any())).thenReturn(Optional.empty());

        PatientNotFoundException exception = assertThrows(PatientNotFoundException.class, () -> patientService.findPatientByEmail(any()));
        assertEquals("Patient not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }




}
