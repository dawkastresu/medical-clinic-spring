package com.dawkastresu.medicalclinic;

import com.dawkastresu.medicalclinic.command.CreateAppointmentCommand;
import com.dawkastresu.medicalclinic.dto.AppointmentDto;
import com.dawkastresu.medicalclinic.dto.SimpleDoctorDto;
import com.dawkastresu.medicalclinic.model.Specialization;
import com.dawkastresu.medicalclinic.service.AppointmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private AppointmentService service;

    @Test
    public void getAll_AppointmentsExist_AppointmentsDtoListReturned() throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);
        SimpleDoctorDto simpleDoctorDto = new SimpleDoctorDto(1L, "email@gmail.com", "firstName", "lastName", Specialization.CARDIOLOGY);

        List<AppointmentDto> appointmentDto = List.of(
                new AppointmentDto(1L, startTime, endTime, 1L, simpleDoctorDto)
        );

        when(service.getAll()).thenReturn(appointmentDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/appointments")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].doctor.email").value("email@gmail.com"))
                .andExpect(jsonPath("$[0].patientId").value(1))
                .andExpect(jsonPath("$[0].doctor.id").value(1));
    }

    @Test
    public void addNew_AppointmentCreated_AppointmentDtoReturned() throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);
        SimpleDoctorDto simpleDoctorDto = new SimpleDoctorDto(1L, "email@gmail.com", "firstName", "lastName", Specialization.CARDIOLOGY);
        CreateAppointmentCommand command = new CreateAppointmentCommand(startTime, endTime, 1L, 1L);
        AppointmentDto appointmentDto = new AppointmentDto(1L, startTime, endTime, 1L, simpleDoctorDto);

        when(service.addNew(command)).thenReturn(appointmentDto);

        mockMvc.perform(post("/appointments")
                .content(objectMapper.writeValueAsString(command))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.patientId").value(1));
    }

//    @Test
//    public void assignPatient_PatientExists_PatientDtoReturned() throws Exception {
//        LocalDate birthDate = LocalDate.of(1998, 4, 24);
//        Patient patient = new Patient(1L, "email@gmail.com", "idCardNo", "firstName", "lastName", "phoneNumber", birthDate, new User());
//        AssignPatientCommand assignPatientCommand = new AssignPatientCommand(1L);
//        LocalDateTime startTime = LocalDateTime.now();
//        LocalDateTime endTime = startTime.plusHours(1);
//        SimpleDoctorDto simpleDoctorDto = new SimpleDoctorDto(1L, "email@gmail.com", "firstName", "lastName", Specialization.CARDIOLOGY);
//        AppointmentDto appointmentDto = new AppointmentDto(1L, startTime, endTime, 1L, simpleDoctorDto);
//
//        when(service.addPatient(assignPatientCommand.getId(), 1L)).thenReturn(appointmentDto);
//
//        mockMvc.perform(patch("/appointments/{appointmentId}", 1L)
//                .content(objectMapper.writeValueAsString(assignPatientCommand))
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.patientId").value(1L))
//                .andExpect(jsonPath("$.doctor.id").value(1L));
//
//    }

    @Test
    public void getAppointmentsForPatient_PatientAndAppointmentExist_AppointmentDtoListReturned() throws Exception {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);
        SimpleDoctorDto simpleDoctorDto = new SimpleDoctorDto(1L, "email@gmail.com", "firstName", "lastName", Specialization.CARDIOLOGY);
        List<AppointmentDto> appointmentDtos = List.of(
                new AppointmentDto(1L, startTime, endTime, 1L, simpleDoctorDto),
                new AppointmentDto(2L, startTime, endTime, 1L, simpleDoctorDto)
        );

        when(service.findByPatientId(any())).thenReturn(appointmentDtos);

        mockMvc.perform(get("/appointments/{patientId}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }
}
