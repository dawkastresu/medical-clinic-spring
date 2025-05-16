package com.dawkastresu.medicalclinic;

import com.dawkastresu.medicalclinic.command.RegisterDoctorCommand;
import com.dawkastresu.medicalclinic.dto.DoctorDto;
import com.dawkastresu.medicalclinic.model.Doctor;
import com.dawkastresu.medicalclinic.model.Institution;
import com.dawkastresu.medicalclinic.model.Specialization;
import com.dawkastresu.medicalclinic.repository.DoctorRepository;
import com.dawkastresu.medicalclinic.service.DoctorService;
import com.dawkastresu.medicalclinic.utils.DoctorMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DoctorControllerTest {

    @MockitoBean
    private DoctorService service;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private DoctorMapper doctorMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private DoctorRepository repository;



    @Test
    public void getAllDoctors_DoctorsExist_ReturnDoctorDtoList() throws Exception {
        //given
        List<DoctorDto> doctors = List.of(
                new DoctorDto(1L, "email@gmail.com", "firstName", "lastName", Specialization.CARDIOLOGY, new ArrayList<>()),
                new DoctorDto(2L, "email2@gmail.com", "firstName2", "lastName2", Specialization.CARDIOLOGY, new ArrayList<>()),
                new DoctorDto(3L, "email3@gmail.com", "firstName3", "lastName3", Specialization.CARDIOLOGY, new ArrayList<>())
        );

        when(service.getAll()).thenReturn(doctors);

        mockMvc.perform(MockMvcRequestBuilders.get("/doctors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(doctors)));
    }

    @Test
    public void getDoctorById_DoctorExists_ReturnDoctorDto() throws Exception {
        DoctorDto doctorDto = new DoctorDto(1L, "email@gmail.com", "firstName", "lastName", Specialization.CARDIOLOGY, new ArrayList<>());

        when(service.findById(any())).thenReturn(doctorDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/doctors/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(doctorDto)));
    }

    @Test
    public void addNewDoctor_DoctorCreated_ReturnDoctorDto() throws Exception{
        DoctorDto doctorDto = new DoctorDto(1L, "email@gmail.com", "firstName", "lastName", Specialization.CARDIOLOGY, new ArrayList<>());
        RegisterDoctorCommand command = new RegisterDoctorCommand("email@gmail.com", "password", "firstName", "lastName", Specialization.CARDIOLOGY, "fullName", "postalCode", "adress", new ArrayList<>());

        when(service.addNew(any())).thenReturn(doctorDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/doctors")
                .content(objectMapper.writeValueAsString(command))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("email@gmail.com"))
                .andExpect(jsonPath("$.firstName").value("firstName"))
                .andExpect(jsonPath("$.lastName").value("lastName"))
                .andExpect(jsonPath("$.specialization").value("CARDIOLOGY"))
                .andExpect(jsonPath("$.institutions").isEmpty());
    }

    @Test
    public void deleteDoctor_DoctorExists() throws Exception{
        Doctor doctor = new Doctor(1L, "email@gmail.com", "password", "firstName", "lastName", new ArrayList<>(), Specialization.CARDIOLOGY, new ArrayList<>());

        mockMvc.perform(delete("/doctors/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
    }

    @Test
    public void editDoctorById_DoctorExists_ReturnDoctorDto() throws Exception {
        Doctor doctor = new Doctor(1L, "email@gmail.com", "password", "firstName", "lastName", new ArrayList<>(), Specialization.CARDIOLOGY, new ArrayList<>());
        RegisterDoctorCommand command = new RegisterDoctorCommand("edit@gmail.com", "editPassword", "firstNameEdit", "lastNameEdit", Specialization.CARDIOLOGY, "fullNameEdit", "postalCodeEdit", "adressEdit", new ArrayList<>());
        DoctorDto doctorDto = new DoctorDto(1L, "email@gmail.com", "firstName", "lastName", Specialization.CARDIOLOGY, new ArrayList<>());

        when(service.editById(any(), any())).thenReturn(doctorDto);

        mockMvc.perform(put("/doctors/{id}", 1L)
                .content(objectMapper.writeValueAsString(command))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("email@gmail.com"))
                .andExpect(jsonPath("$.firstName").value("firstName"))
                .andExpect(jsonPath("$.lastName").value("lastName"))
                .andExpect(jsonPath("$.specialization").value("CARDIOLOGY"))
                .andExpect(jsonPath("$.institutions").isEmpty());
    }

    @Test
    public void addDoctorToInstitution() throws Exception {
        Doctor doctor = new Doctor(1L, "email@gmail.com", "password", "firstName", "lastName", new ArrayList<>(), Specialization.CARDIOLOGY, new ArrayList<>());
        Institution institution = new Institution(1L, "name", "postalCode", "adress", new ArrayList<>());

        mockMvc.perform(patch("/doctors/{id}", 1L)
                .content(objectMapper.writeValueAsString(institution.getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
