package com.dawkastresu.medicalclinic;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="USERS")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Patient patient;

}
