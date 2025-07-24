package com.dawkastresu.medicalclinic;

import com.dawkastresu.medicalclinic.command.CreatePatientCommand;
import com.dawkastresu.medicalclinic.command.RegisterPatientCommand;
import com.dawkastresu.medicalclinic.dto.PatientDto;
import com.dawkastresu.medicalclinic.dto.UserDto;
import com.dawkastresu.medicalclinic.model.Patient;
import com.dawkastresu.medicalclinic.model.User;
import com.dawkastresu.medicalclinic.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
    public void getAll_PatientsExist_PatientDtoPageReturned() throws Exception {
        //given
        LocalDate birthDate = LocalDate.of(1998, 4, 24);
        List<PatientDto> patientsDto = List.of(
                new PatientDto(1L, "email@gmail.com", "firstName", "lastName", "phoneNumber", birthDate, "fullName", "idCardNo", new UserDto(1L, "username")),
                new PatientDto(2L, "email2@gmail.com", "firstName2", "lastName2", "phoneNumber2", birthDate, "fullName2", "idCardNo2", new UserDto(2L, "username2"))
        );

        Pageable pageable = PageRequest.of(0, 10);
        Page<PatientDto> patientPage = new PageImpl<>(patientsDto, pageable, patientsDto.size());

        when(service.getAll(any(Pageable.class))).thenReturn(patientPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/patients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].email").value("email@gmail.com"))
                .andExpect(jsonPath("$.content[0].firstName").value("firstName"))
                .andExpect(jsonPath("$.content[0].lastName").value("lastName"))
                .andExpect(jsonPath("$.content[0].phoneNumber").value("phoneNumber"))
                .andExpect(jsonPath("$.content[0].birthDate").value("1998-04-24"))
                .andExpect(jsonPath("$.content[0].fullName").value("fullName"))
                .andExpect(jsonPath("$.content[0].idCardNo").value("idCardNo"))
                .andExpect(jsonPath("$.content[0].user.id").value(1L))
                .andExpect(jsonPath("$.content[0].user.username").value("username"))
                .andExpect(jsonPath("$.content[1].id").value(2L))
                .andExpect(jsonPath("$.content[1].email").value("email2@gmail.com"))
                .andExpect(jsonPath("$.content[1].firstName").value("firstName2"))
                .andExpect(jsonPath("$.content[1].lastName").value("lastName2"))
                .andExpect(jsonPath("$.content[1].phoneNumber").value("phoneNumber2"))
                .andExpect(jsonPath("$.content[1].user.id").value(2L))
                .andExpect(jsonPath("$.content[1].user.username").value("username2"))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.last").value(true));
    }

    @Test
    public void getPatientByMail_PatientExists_ReturnPatientDto() throws Exception {
        LocalDate birthDate = LocalDate.of(1998, 4, 24);
        PatientDto patientDto = new PatientDto(
                1L,
                "email@gmail.com",
                "firstName",
                "lastName",
                "phoneNumber",
                birthDate,
                "fullName",
                "idCardNo",
                new UserDto(1L, "username")
        );

        when(service.findPatientByEmail(any())).thenReturn(patientDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/patients/{email}", "email@gmail.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("email@gmail.com"))
                .andExpect(jsonPath("$.firstName").value("firstName"))
                .andExpect(jsonPath("$.lastName").value("lastName"))
                .andExpect(jsonPath("$.idCardNo").value("idCardNo"))
                .andExpect(jsonPath("$.user.id").value(1));
    }

    @Test
    public void registerPatient_PatientCreated_ReturnPatientDto() throws Exception {
        LocalDate birthDate = LocalDate.of(1998, 4, 24);
        PatientDto patientDto = new PatientDto(1L, "email@gmail.com", "firstName", "lastName", "phoneNumber", birthDate, "fullName", "idCardNo", new UserDto(1L, "username"));
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
        PatientDto patientDto = new PatientDto(
                1L,
                "email@gmail.com",
                "firstName",
                "lastName",
                "phoneNumber",
                birthDate,
                "fullName",
                "idCardNo",
                null // Usuń problematyczny obiekt User
        );
        CreatePatientCommand command = new CreatePatientCommand(
                "email@gmail.com",
                "password",
                "idCardNo",
                "firstName",
                "lastName",
                "phoneNumber",
                birthDate
        );

        when(service.editByEmail(eq("email@gmail.com"), any(CreatePatientCommand.class)))
                .thenReturn(patientDto);

        mockMvc.perform(put("/patients/{email}", "email@gmail.com")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("email@gmail.com"))
                .andExpect(jsonPath("$.firstName").value("firstName"))
                .andExpect(jsonPath("$.lastName").value("lastName"))  // Uwaga na literówki!
                .andExpect(jsonPath("$.phoneNumber").value("phoneNumber"))
                .andExpect(jsonPath("$.fullName").value("fullName"))
                .andExpect(jsonPath("$.idCardNo").value("idCardNo"));
    }


}
