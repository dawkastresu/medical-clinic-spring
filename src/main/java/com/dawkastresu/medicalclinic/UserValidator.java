package com.dawkastresu.medicalclinic;

import org.springframework.http.HttpStatus;

import java.util.List;

public class UserValidator {

    public static boolean validateUser(UserRepository repository, String username){
        List<User> users = repository.findAll();
        return users.stream()
                .noneMatch(user -> username.equalsIgnoreCase(user.getUsername()));
    }

    public static void validateUserEdit(User user) {
        if (user.getUsername() == null) {
            throw new InvalidUserDataException("User name is null", HttpStatus.BAD_REQUEST);
        }
    }

}
