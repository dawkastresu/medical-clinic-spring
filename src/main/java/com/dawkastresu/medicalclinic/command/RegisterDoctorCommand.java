package com.dawkastresu.medicalclinic.command;

import com.dawkastresu.medicalclinic.model.Specialization;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = ANY)
public class RegisterDoctorCommand {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Specialization specialization;

}
