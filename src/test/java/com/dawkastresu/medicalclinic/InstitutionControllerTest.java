package com.dawkastresu.medicalclinic;

import com.dawkastresu.medicalclinic.command.CreateInstitutionCommand;
import com.dawkastresu.medicalclinic.dto.InstitutionDto;
import com.dawkastresu.medicalclinic.service.InstitutionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
public class InstitutionControllerTest {

    @MockitoBean
    private InstitutionService service;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAll_InstitutionsExist_InstitutionDtoListReturned() throws Exception {
        List<InstitutionDto> institutionDtos = List.of(
                new InstitutionDto(1L,"name", "postalCode", "adress", new ArrayList<>()),
                new InstitutionDto(2L,"name2", "postalCode2", "adress2", new ArrayList<>()),
                new InstitutionDto(3L,"name3", "postalCode3", "adress3", new ArrayList<>())
        );

        when(service.getAll()).thenReturn(institutionDtos);

        mockMvc.perform(get("/institutions")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$[0].name").value("name"))
                .andExpect(jsonPath("$[1].name").value("name2"))
                .andExpect(jsonPath("$[2].name").value("name3"));
    }

    @Test
    public void addNew_InstitutionCreated_InstitutionDtoReturned() throws Exception {
        InstitutionDto institutionDto = new InstitutionDto(1L,"name", "postalCode", "adress", new ArrayList<>());
        CreateInstitutionCommand command = new CreateInstitutionCommand("name", "postalCode", "adress");

        when(service.addNew(command)).thenReturn(institutionDto);

        mockMvc.perform(post("/institutions")
                .content(objectMapper.writeValueAsString(command))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id").value("id"))
                .andExpect(jsonPath("$.name").value("id"))
                .andExpect(jsonPath("$.postalCode").value("postalCode"))
                .andExpect(jsonPath("$.adress").value("adress"));
    }
}
