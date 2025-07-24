package com.dawkastresu.medicalclinic.controller;

import com.dawkastresu.medicalclinic.dto.UserDto;
import com.dawkastresu.medicalclinic.model.Doctor;
import com.dawkastresu.medicalclinic.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doctor.class)) }) })
    @GetMapping
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Operation(summary = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doctor.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content =  @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content =  @Content) })
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Remove user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Doctor.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content =  @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content =  @Content) })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUser(@PathVariable Long id) {
        service.remove(id);
    }

}
