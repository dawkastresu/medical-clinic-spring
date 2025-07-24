package com.dawkastresu.medicalclinic;

import com.dawkastresu.medicalclinic.command.CreateInstitutionCommand;
import com.dawkastresu.medicalclinic.dto.InstitutionDto;
import com.dawkastresu.medicalclinic.service.InstitutionService;
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

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
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
    public void getAll_InstitutionsExist_InstitutionDtoPageReturned() throws Exception {
        //given
        List<InstitutionDto> institutionDtos = List.of(
                new InstitutionDto(1L,"name", "postalCode", "adress", new ArrayList<>()),
                new InstitutionDto(2L,"name2", "postalCode2", "adress2", new ArrayList<>()),
                new InstitutionDto(3L,"name3", "postalCode3", "adress3", new ArrayList<>())
        );

        Pageable pageable = PageRequest.of(0, 10);
        Page<InstitutionDto> institutionPage = new PageImpl<>(institutionDtos, pageable, institutionDtos.size());

        when(service.getAll(any(Pageable.class))).thenReturn(institutionPage);

        mockMvc.perform(get("/institutions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("name"))
                .andExpect(jsonPath("$.content[0].postalCode").value("postalCode"))
                .andExpect(jsonPath("$.content[0].adress").value("adress"))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[1].name").value("name2"))
                .andExpect(jsonPath("$.content[1].postalCode").value("postalCode2"))
                .andExpect(jsonPath("$.content[1].adress").value("adress2"))
                .andExpect(jsonPath("$.content[2].id").value(3))
                .andExpect(jsonPath("$.content[2].name").value("name3"))
                .andExpect(jsonPath("$.content[2].postalCode").value("postalCode3"))
                .andExpect(jsonPath("$.content[2].adress").value("adress3"))
                .andExpect(jsonPath("$.totalElements").value(3))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.last").value(true));
    }

    @Test
    public void addNew_InstitutionCreated_InstitutionDtoReturned() throws Exception {
        InstitutionDto institutionDto = new InstitutionDto(1L, "name", "postalCode", "adress", new ArrayList<>());
        CreateInstitutionCommand command = new CreateInstitutionCommand("name", "postalCode", "adress");

        when(service.addNew(any())).thenReturn(institutionDto);

        mockMvc.perform(post("/institutions")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.postalCode").value("postalCode"))
                .andExpect(jsonPath("$.adress").value("adress"));
    }
}
