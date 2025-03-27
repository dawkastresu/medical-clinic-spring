package com.dawkastresu.medicalclinic;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PUBLIC)
@JsonAutoDetect(fieldVisibility = ANY)
public class PatientDto {
    String email;
    String firstName;
    String lastName;
    String phoneNumber;
    LocalDate birthday;
}
