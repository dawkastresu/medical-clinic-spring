package com.dawkastresu.medicalclinic.command;

import com.dawkastresu.medicalclinic.model.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@JsonAutoDetect(fieldVisibility = ANY)
public class RegisterPatientCommand {

    String email;
    String idCardNo;
    String firstName;
    String lastName;
    String phoneNumber;
    LocalDate birthday;
    String username;
    String password;
    User user;

}
