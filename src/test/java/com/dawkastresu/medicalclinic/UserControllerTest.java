package com.dawkastresu.medicalclinic;

import com.dawkastresu.medicalclinic.dto.UserDto;
import com.dawkastresu.medicalclinic.model.Patient;
import com.dawkastresu.medicalclinic.model.User;
import com.dawkastresu.medicalclinic.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockitoBean
    private UserService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllUsers_UsersExist_ReturnUserDtoList() throws Exception {

        List<UserDto> users = List.of(
                new UserDto(1L, "username1"),
                new UserDto(2L, "username2"),
                new UserDto(3L, "username3")
        );

        when(service.getAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("username1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].username").value("username2"));
    }

    @Test
    public void getUserById_UserExists_ReturnUserDto() throws Exception {
        UserDto userDto = new UserDto(1L, "username");
        User user = new User(1L, "username", "password", new Patient());

        when(service.findById(1L)).thenReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("username"));
    }

    @Test
    public void removeUser_UserExists() throws Exception {
        User user = new User(1L, "username", "password", new Patient());

        mockMvc.perform(delete("/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
