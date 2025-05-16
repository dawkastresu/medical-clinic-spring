package com.dawkastresu.medicalclinic.service;

import com.dawkastresu.medicalclinic.command.CreateUserCommand;
import com.dawkastresu.medicalclinic.utils.UserMapper;
import com.dawkastresu.medicalclinic.repository.UserRepository;
import com.dawkastresu.medicalclinic.utils.UserValidator;
import com.dawkastresu.medicalclinic.dto.UserDto;
import com.dawkastresu.medicalclinic.exception.UserNotFoundException;
import com.dawkastresu.medicalclinic.model.Password;
import com.dawkastresu.medicalclinic.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public List<UserDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public UserDto addNew(CreateUserCommand command) {
        User user = mapper.toEntity(command);
        if (UserValidator.validateUser(repository, user.getUsername())) {
            repository.save(user);
        }
        return mapper.toDto(user);
    }

    public void remove(Long id) {
        repository.deleteById(id);
    }

    public UserDto findById(Long id) {
        User user = repository.findById(id).
                orElseThrow(() -> new UserNotFoundException("User not found", HttpStatus.NOT_FOUND));
        return mapper.toDto(user);
    }

    public void editPasswordById(Long id, Password password) {
        User user = repository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found", HttpStatus.NOT_FOUND));
        user.setPassword(password.getPassword());
    }

}
