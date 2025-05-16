package com.dawkastresu.medicalclinic.command;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.experimental.FieldDefaults;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@JsonAutoDetect(fieldVisibility = ANY)
public class CreatePatientCommand {

    String email;
    String password;
    String idCardNo;
    String firstName;
    String lastName;
    String phoneNumber;
    LocalDate birthday;

}
