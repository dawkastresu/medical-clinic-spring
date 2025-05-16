package com.dawkastresu.medicalclinic.utils;

import com.dawkastresu.medicalclinic.exception.InvalidUserDataException;
import com.dawkastresu.medicalclinic.model.User;
import com.dawkastresu.medicalclinic.repository.UserRepository;
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
