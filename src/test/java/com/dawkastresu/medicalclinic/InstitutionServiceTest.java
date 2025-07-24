package com.dawkastresu.medicalclinic;

import com.dawkastresu.medicalclinic.command.CreateInstitutionCommand;
import com.dawkastresu.medicalclinic.dto.InstitutionDto;
import com.dawkastresu.medicalclinic.exception.DoctorNotFoundException;
import com.dawkastresu.medicalclinic.exception.InstitutionNotFoundException;
import com.dawkastresu.medicalclinic.model.Institution;
import com.dawkastresu.medicalclinic.model.Specialization;
import com.dawkastresu.medicalclinic.repository.DoctorRepository;
import com.dawkastresu.medicalclinic.repository.InstitutionRepository;
import com.dawkastresu.medicalclinic.service.DoctorService;
import com.dawkastresu.medicalclinic.service.InstitutionService;
import com.dawkastresu.medicalclinic.utils.DoctorMapper;
import com.dawkastresu.medicalclinic.utils.DoctorValidator;
import com.dawkastresu.medicalclinic.utils.InstitutionMapper;
import com.dawkastresu.medicalclinic.utils.InstitutionValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InstitutionServiceTest {

    InstitutionRepository institutionRepository;
    InstitutionMapper institutionMapper;
    InstitutionService institutionService;

    @BeforeEach
    void setup() {
        this.institutionMapper = Mappers.getMapper(InstitutionMapper.class);
        this.institutionRepository = Mockito.mock(InstitutionRepository.class);
        this.institutionService = new InstitutionService(institutionRepository, institutionMapper);
    }

    @Test
    void getAll_institutionsExist_institutionDtoPageReturned() throws Exception {
        //given
        List<Institution> institutions = List.of(
                new Institution(1L, "name", "postalCode", "adress", new ArrayList<>()),
                new Institution(2L, "name2", "postalCode2", "adress2", new ArrayList<>())
        );

        Pageable pageable = PageRequest.of(0, 10);
        Page<Institution> institutionPage = new PageImpl<>(institutions, pageable, institutions.size());

        when(institutionRepository.findAll(any(Pageable.class))).thenReturn(institutionPage);

        //when
        Page<InstitutionDto> result = institutionService.getAll(pageable);

        //then
        Assertions.assertAll(
                () -> assertEquals(2, result.getContent().size()),
                () -> assertEquals(1L, result.getContent().get(0).getId()),
                () -> assertEquals(2L, result.getContent().get(1).getId()),
                () -> assertEquals("name", result.getContent().get(0).getName()),
                () -> assertEquals("name2", result.getContent().get(1).getName()),
                () -> assertEquals("postalCode", result.getContent().get(0).getPostalCode()),
                () -> assertEquals("adress", result.getContent().get(0).getAdress()),
                () -> assertEquals(2, result.getTotalElements()),
                () -> assertEquals(1, result.getTotalPages()),
                () -> assertEquals(0, result.getNumber()),
                () -> assertEquals(10, result.getSize()),
                () -> assertTrue(result.isFirst()),
                () -> assertTrue(result.isLast()),
                () -> assertFalse(result.isEmpty())
        );
    }

    @Test
    void addNew_institutionCreated_institutionDtoReturned() throws Exception {
        CreateInstitutionCommand command = new CreateInstitutionCommand("name", "postalCode", "adress");

        // Mockowanie validatora (bo to metoda statyczna)
        try (var mockedValidator = Mockito.mockStatic(InstitutionValidator.class)) {
            mockedValidator.when(() -> InstitutionValidator.validateInstitution(any(), any()))
                    .thenReturn(true);

            // Mockowanie save() – nadajemy ID zwróconej encji
            when(institutionRepository.save(any(Institution.class)))
                    .thenAnswer(invocation -> {
                        Institution inst = invocation.getArgument(0);
                        inst.setId(1L);
                        return inst;
                    });

            // Wykonanie testu
            InstitutionDto result = institutionService.addNew(command);

            // Sprawdzenie
            Assertions.assertAll(
                    () -> assertEquals(1L, result.getId()),
                    () -> assertEquals("name", result.getName()),
                    () -> assertEquals("postalCode", result.getPostalCode()),
                    () -> assertEquals("adress", result.getAdress())
            );
        }
    }

    @Test
    void remove_institutionRemoved() throws Exception {
        String name = "name";
        institutionService.remove(name);

        verify(institutionRepository).deleteByName(name);
    }

    @Test
    void getAll_institutionNotFound() {
        //given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Institution> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(institutionRepository.findAll(any(Pageable.class))).thenReturn(emptyPage);

        //when & then
        InstitutionNotFoundException exception = assertThrows(InstitutionNotFoundException.class,
                () -> institutionService.getAll(pageable));

        assertEquals("No institutions found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }


}
