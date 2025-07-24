package com.dawkastresu.medicalclinic;

import com.dawkastresu.medicalclinic.command.CreateUserCommand;
import com.dawkastresu.medicalclinic.dto.UserDto;
import com.dawkastresu.medicalclinic.model.Institution;
import com.dawkastresu.medicalclinic.model.Password;
import com.dawkastresu.medicalclinic.model.Patient;
import com.dawkastresu.medicalclinic.model.User;
import com.dawkastresu.medicalclinic.repository.InstitutionRepository;
import com.dawkastresu.medicalclinic.repository.UserRepository;
import com.dawkastresu.medicalclinic.service.InstitutionService;
import com.dawkastresu.medicalclinic.service.UserService;
import com.dawkastresu.medicalclinic.utils.InstitutionMapper;
import com.dawkastresu.medicalclinic.utils.InstitutionValidator;
import com.dawkastresu.medicalclinic.utils.UserMapper;
import com.dawkastresu.medicalclinic.utils.UserValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    UserRepository userRepository;
    UserMapper userMapper;
    UserService userService;

    @BeforeEach
    void setup() {
        this.userMapper = Mappers.getMapper(UserMapper.class);
        this.userRepository = Mockito.mock(UserRepository.class);
        this.userService = new UserService(userRepository, userMapper);
    }

//    @Test
//    void getAll_usersExist_userDtoListReturned() throws Exception {
//        List<User> users = List.of(
//                new User(1L, "username", "password", null),
//                new User(2L, "username2", "password2", null)
//        );
//
//        when(userRepository.findAll()).thenReturn(users);
//
//        List<UserDto> result = userService.getAll();
//
//        Assertions.assertAll(
//                () -> assertEquals(1L, result.get(0).getId()),
//                () -> assertEquals(2L, result.get(1).getId()),
//                () -> assertEquals("username", result.get(0).getUsername())
//        );
//    }

    @Test
    void addNew_userCreated_userDtoReturned() throws Exception {
        CreateUserCommand command = new CreateUserCommand("username", "password");

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> {
                    User user = invocation.getArgument(0);
                    user.setId(1L);
                    return user;
                });

        try (var mockedValidator = Mockito.mockStatic(UserValidator.class)) {
            mockedValidator.when(() -> UserValidator.validateUser(any(), any()))
                    .thenReturn(true);
        }

        UserDto result = userService.addNew(command);

        Assertions.assertAll(
                () -> assertEquals(1L, result.getId()),
                () -> assertEquals("username", result.getUsername())
        );
    }

    @Test
    void remove_userRemoved() throws Exception {
        long id = 1;
        userService.remove(id);

        verify(userRepository).deleteById(id);
    }

    @Test
    void editPasswordById_passwordUpdated() {
        // given
        long id = 1;
        Password password = new Password("newPassword");
        User user = new User();
        user.setId(id);
        user.setPassword("oldPassword");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // when
        userService.editPasswordById(id, password);

        // then
        verify(userRepository).findById(id);
        assertEquals("newPassword", user.getPassword());
    }



}
