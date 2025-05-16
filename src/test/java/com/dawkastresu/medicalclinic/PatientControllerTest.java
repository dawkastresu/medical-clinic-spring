package com.dawkastresu.medicalclinic;

import com.dawkastresu.medicalclinic.command.CreatePatientCommand;
import com.dawkastresu.medicalclinic.command.RegisterPatientCommand;
import com.dawkastresu.medicalclinic.dto.PatientDto;
import com.dawkastresu.medicalclinic.model.Patient;
import com.dawkastresu.medicalclinic.model.User;
import com.dawkastresu.medicalclinic.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerTest {

    @MockitoBean
    private PatientService service;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAll_PatientsExist_PatientDtoListReturned() throws Exception {
        LocalDate birthDate = LocalDate.of(1998, 4, 24);
        List<PatientDto> patientsDto = List.of(
                new PatientDto(1L, "email@gmail.com", "firstName", "lastName", "phoneNumber", birthDate, "fullName", "idCardNo", new User()),
                new PatientDto(2L, "email2@gmail.com", "firstName2", "lastName2", "phoneNumber2", birthDate, "fullName2", "idCardNo2", new User())
        );

        when(service.getAll()).thenReturn(patientsDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/patients")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].email").value("email@gmail.com"))
                .andExpect(jsonPath("$[0].firstName").value("firstName"))
                .andExpect(jsonPath("$[0].lastName").value("lastName"))
                .andExpect(jsonPath("$[0].phoneNumber").value("phoneNumber"));
    }

    @Test
    public void getPatientByMail_PatientExists_ReturnPatientDto() throws Exception {
        LocalDate birthDate = LocalDate.of(1998, 4, 24);
        PatientDto patientDto = new PatientDto(1L, "email@gmail.com", "firstName", "lastName", "phoneNumber", birthDate, "fullName", "idCardNo", new User());
        Patient patient = new Patient(1L, "email@gmail.com", "idCardNo", "firstName", "lastName", "phoneNumber", birthDate, new User());

        when(service.findPatientByEmail("email@gmail.com")).thenReturn(patientDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/patients/{email}", "email@gmail.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("email@gmail.com"))
                .andExpect(jsonPath("$.firstName").value("firstName"))
                .andExpect(jsonPath("$.lastName").value("lastName"))
                .andExpect(jsonPath("$.idCardNo").value("idCardNo"))
                .andExpect(jsonPath("$.user").value(new User()));
    }

    @Test
    public void registerPatient_PatientCreated_ReturnPatientDto() throws Exception {
        LocalDate birthDate = LocalDate.of(1998, 4, 24);
        PatientDto patientDto = new PatientDto(1L, "email@gmail.com", "firstName", "lastName", "phoneNumber", birthDate, "fullName", "idCardNo", new User());
        RegisterPatientCommand command = new RegisterPatientCommand("email@gmail.com", "idCardNo", "firstName", "lastName", "phoneNumber", birthDate, "username", "password", new User());

        when(service.addNew(any())).thenReturn(patientDto);

        mockMvc.perform(post("/patients")
                .content(objectMapper.writeValueAsString(command))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("email@gmail.com"))
                .andExpect(jsonPath("$.firstName").value("firstName"))
                .andExpect(jsonPath("$.lastName").value("lastName"))
                .andExpect(jsonPath("$.phoneNumber").value("phoneNumber"))
                .andExpect(jsonPath("$.fullName").value("fullName"));
    }

    @Test
    public void removePatient_PatientExists() throws Exception {
        LocalDate birthDate = LocalDate.of(1998, 4, 24);
        Patient patient = new Patient(1L, "email@gmail.com", "idCardNo", "firstName", "lastName", "phoneNumber", birthDate, new User());

        mockMvc.perform(delete("/patients/{email}", "email@gmail.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void editPatientByMail_PatientExists_ReturnPatientDto() throws Exception {
        LocalDate birthDate = LocalDate.of(1998, 4, 24);
        PatientDto patientDto = new PatientDto(1L, "email@gmail.com", "firstName", "lastName", "phoneNumber", birthDate, "fullName", "idCardNo", new User());
        CreatePatientCommand command = new CreatePatientCommand("email@gmail.com", "password", "idCardNo", "firstName", "lastName", "phoneNumber", birthDate);

        when(service.editByEmail("email@gmail.com", command)).thenReturn(patientDto);

        mockMvc.perform(put("/patients/{email}", "email@gmail.com")
                .content(objectMapper.writeValueAsString(command))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("email@gmail.com"))
                .andExpect(jsonPath("$.firstName").value("firstName"))
                .andExpect(jsonPath("$.lastname").value("lastName"))
                .andExpect(jsonPath("$.phoneNumber").value("phoneNumber"))
                .andExpect(jsonPath("$.fullName").value("fullName"))
                .andExpect(jsonPath("$.idCardNo").value("idCardNo"))
                .andExpect(jsonPath("$.user").value(new User()));

    }

}
