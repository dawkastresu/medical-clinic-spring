package com.dawkastresu.medicalclinic.utils;

import com.dawkastresu.medicalclinic.command.CreateUserCommand;
import com.dawkastresu.medicalclinic.dto.UserDto;
import com.dawkastresu.medicalclinic.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(CreateUserCommand command);
    UserDto toDto(User user);

}
