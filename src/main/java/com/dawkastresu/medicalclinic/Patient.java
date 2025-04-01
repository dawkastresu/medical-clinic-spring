package com.dawkastresu.medicalclinic;

import lombok.Data;

import java.time.LocalDate;
//Adnotacja Data pozwala wygenerować potrzebne boilerplate'y - Konstruktory, Gettery, Settery, Equals hash code oraz toString.
@Data
public class Patient {
    private String email;
    private String password;
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;
}
