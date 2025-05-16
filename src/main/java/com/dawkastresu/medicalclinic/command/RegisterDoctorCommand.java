package com.dawkastresu.medicalclinic.command;

import com.dawkastresu.medicalclinic.model.Specialization;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@JsonAutoDetect(fieldVisibility = ANY)
public class RegisterDoctorCommand {

    String email;
    String password;
    String firstName;
    String lastName;
    Specialization specialization;
    String name;
    String postalCode;
    String adress;
    List<Long> institutionIds;

}
