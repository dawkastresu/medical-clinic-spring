package com.dawkastresu.medicalclinic;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(CreateUserCommand command);
    UserDto toDto(User user);

}
