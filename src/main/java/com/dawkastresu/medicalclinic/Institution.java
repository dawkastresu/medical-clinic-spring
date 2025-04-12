package com.dawkastresu.medicalclinic;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="INSTITUTIONS")
@Data
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String postalCode;

    private String adress;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Doctor_Institution",
            joinColumns = { @JoinColumn(name = "doctor_id") },
            inverseJoinColumns = { @JoinColumn(name = "institution_id")})
    private List<Doctor> doctors;

}
