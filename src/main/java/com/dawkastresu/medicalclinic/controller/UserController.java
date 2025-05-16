package com.dawkastresu.medicalclinic.controller;

import com.dawkastresu.medicalclinic.dto.UserDto;
import com.dawkastresu.medicalclinic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUser(@PathVariable Long id) {
        service.remove(id);
    }

}
